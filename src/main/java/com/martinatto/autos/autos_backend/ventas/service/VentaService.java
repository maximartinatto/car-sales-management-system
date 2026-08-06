package com.martinatto.autos.autos_backend.ventas.service;

import com.martinatto.autos.autos_backend.autos.entity.Auto;
import com.martinatto.autos.autos_backend.autos.enums.EstadoAuto;
import com.martinatto.autos.autos_backend.autos.repository.IAutoRepository;
import com.martinatto.autos.autos_backend.clientes.entity.Cliente;
import com.martinatto.autos.autos_backend.clientes.repository.IClientesRepository;
import com.martinatto.autos.autos_backend.shared.exception.ResourceNotFoundException;
import com.martinatto.autos.autos_backend.ventas.dto.VentaRequestDTO;
import com.martinatto.autos.autos_backend.ventas.dto.VentaResponseDTO;
import com.martinatto.autos.autos_backend.ventas.entity.Venta;
import com.martinatto.autos.autos_backend.ventas.enums.EstadoVenta;
import com.martinatto.autos.autos_backend.ventas.mapper.VentaMapper;
import com.martinatto.autos.autos_backend.ventas.repository.IVentaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VentaService implements IVentaService {

    private final IVentaRepository ventaRepo;
    private final IAutoRepository autoRepo;
    private final IClientesRepository clienteRepo;

    @Override
    @Transactional
    public VentaResponseDTO crearVenta(VentaRequestDTO request) {

        Auto auto = autoRepo.findById(request.getAutoId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Auto no encontrado"));

        Cliente cliente = clienteRepo.findById(request.getClienteId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Cliente no encontrado"));

        if (auto.getEstado() != EstadoAuto.DISPONIBLE) {
            throw new IllegalStateException(
                    "El auto debe estar disponible para poder venderse"
            );
        }

        Venta venta = VentaMapper.toEntity(request);
        venta.setAuto(auto);
        venta.setCliente(cliente);

        /*
         * Como la creación representa una venta confirmada,
         * el backend establece el estado final.
         */
        venta.setEstado(EstadoVenta.FINALIZADA);
        auto.setEstado(EstadoAuto.VENDIDO);

        Venta ventaGuardada = ventaRepo.save(venta);

        /*
         * No es indispensable llamar autoRepo.save(auto), porque el Auto
         * está administrado dentro de la transacción. Hibernate detectará
         * el cambio de estado mediante dirty checking.
         */

        return VentaMapper.toDTO(ventaGuardada);
    }

    @Override
    @Transactional(readOnly = true)
    public List<VentaResponseDTO> obtenerVentas() {
        return ventaRepo.findAll()
                .stream()
                .map(VentaMapper::toDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public VentaResponseDTO obtenerVentaPorId(Long id) {
        Venta venta = ventaRepo.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Venta no encontrada"));

        return VentaMapper.toDTO(venta);
    }

    @Override
    @Transactional
    public VentaResponseDTO actualizarVenta(
            Long id,
            VentaRequestDTO request
    ) {
        Venta venta = ventaRepo.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Venta no encontrada"));

        venta.setPrecioFinal(request.getPrecioFinal());
        venta.setEstado(request.getEstado());

        /*
         * No conviene reemplazar fecha con LocalDateTime.now():
         * fecha representa cuándo se realizó la venta.
         */

        Venta ventaActualizada = ventaRepo.save(venta);

        return VentaMapper.toDTO(ventaActualizada);
    }

    @Override
    @Transactional
    public void eliminarVenta(Long id) {
        Venta venta = ventaRepo.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Venta no encontrada"));

        /*
         * Si se elimina una venta finalizada, el auto vuelve a estar
         * disponible. Esta regla puede cambiar si preferís no permitir
         * borrar ventas y usar solamente CANCELADA.
         */
        if (venta.getAuto() != null) {
            venta.getAuto().setEstado(EstadoAuto.DISPONIBLE);
        }

        ventaRepo.delete(venta);
    }

    @Override
    @Transactional(readOnly = true)
    public List<VentaResponseDTO> obtenerVentasPorCliente(Long clienteId) {
        if (!clienteRepo.existsById(clienteId)) {
            throw new ResourceNotFoundException(
                    "Cliente no encontrado"
            );
        }

        return ventaRepo.findByClienteId(clienteId)
                .stream()
                .map(VentaMapper::toDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<VentaResponseDTO> obtenerVentasPorEstado(EstadoVenta estadoVenta) {
        return ventaRepo.findByEstado(estadoVenta)
                .stream()
                .map(VentaMapper::toDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<VentaResponseDTO> obtenerVentasPorFecha(LocalDate fechaDesde, LocalDate fechaHasta) {
        if (fechaDesde.isAfter(fechaHasta)) {
            throw new IllegalStateException(
                    "La fecha inicial no puede ser posterior a la fecha final"
            );
        }

        LocalDateTime inicio = fechaDesde.atStartOfDay();
        LocalDateTime fin = fechaHasta.atTime(LocalTime.MAX);

        return ventaRepo.findByFechaBetween(inicio, fin)
                .stream()
                .map(VentaMapper::toDTO)
                .toList();
    }
}