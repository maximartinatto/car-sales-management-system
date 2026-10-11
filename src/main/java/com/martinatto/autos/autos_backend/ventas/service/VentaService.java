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
                    "El vehículo no está disponible para una nueva venta"
            );
        }

        if (request.getEstado() == EstadoVenta.CANCELADA) {
            throw new IllegalArgumentException(
                    "No se puede crear una venta directamente como CANCELADA"
            );
        }

        Venta venta = VentaMapper.toEntity(request);
        venta.setAuto(auto);
        venta.setCliente(cliente);

        if (request.getEstado() == EstadoVenta.EN_PROCESO) {
            auto.setEstado(EstadoAuto.RESERVADO);
        } else if (request.getEstado() == EstadoVenta.FINALIZADA) {
            auto.setEstado(EstadoAuto.VENDIDO);
        }

        Venta ventaGuardada = ventaRepo.save(venta);

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

        EstadoVenta estadoActual = venta.getEstado();
        EstadoVenta nuevoEstado = request.getEstado();

        if (estadoActual != EstadoVenta.EN_PROCESO) {
            throw new IllegalStateException(
                    "Solo se pueden modificar ventas EN_PROCESO"
            );
        }

        Auto auto = venta.getAuto();

        if (nuevoEstado == EstadoVenta.FINALIZADA) {
            auto.setEstado(EstadoAuto.VENDIDO);
        } else if (nuevoEstado == EstadoVenta.CANCELADA) {
            auto.setEstado(EstadoAuto.DISPONIBLE);
        } else {
            auto.setEstado(EstadoAuto.RESERVADO);
        }

        venta.setPrecioFinal(request.getPrecioFinal());
        venta.setEstado(nuevoEstado);

        Venta ventaActualizada = ventaRepo.save(venta);

        return VentaMapper.toDTO(ventaActualizada);
    }

    @Override
    @Transactional
    public void eliminarVenta(Long id) {

        Venta venta = ventaRepo.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Venta no encontrada"));

        if (venta.getEstado() != EstadoVenta.CANCELADA) {
            throw new IllegalStateException(
                    "Solo se pueden eliminar ventas canceladas"
            );
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