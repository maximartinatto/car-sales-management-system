package com.martinatto.autos.autos_backend.pagos.service;

import com.martinatto.autos.autos_backend.pagos.dto.PagoRequestDTO;
import com.martinatto.autos.autos_backend.pagos.dto.PagoResponseDTO;
import com.martinatto.autos.autos_backend.pagos.entity.Pago;
import com.martinatto.autos.autos_backend.pagos.repository.IPagoRepository;
import com.martinatto.autos.autos_backend.shared.exception.ResourceNotFoundException;
import com.martinatto.autos.autos_backend.ventas.entity.Venta;
import com.martinatto.autos.autos_backend.ventas.repository.IVentaRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PagoService implements IPagoService {

    private final IPagoRepository pagoRepo;
    private final IVentaRepository ventaRepo;

    @Override
    @Transactional
    public PagoResponseDTO crearPago(Long ventaId, PagoRequestDTO request) {
        Venta venta = ventaRepo.findById(ventaId)
                .orElseThrow(() -> new ResourceNotFoundException("Venta no encontrada"));

        Pago pago = new Pago();
        pago.setFecha(LocalDateTime.now());
        pago.setMonto(request.getMonto());
        pago.setMetodo(request.getMetodo());
        pago.setVenta(venta);

        Pago guardado = pagoRepo.save(pago);

        return PagoResponseDTO.builder()
                .id(pago.getId())
                .fecha(pago.getFecha())
                .monto(pago.getMonto())
                .metodo(pago.getMetodo())
                .ventaId(pago.getVenta().getId())
                .build();
    }

    @Override
    public List<PagoResponseDTO> obtenerTodos() {
        return pagoRepo.findAll()
                .stream()
                .map(p -> PagoResponseDTO.builder()
                        .id(p.getId())
                        .fecha(p.getFecha())
                        .monto(p.getMonto())
                        .metodo(p.getMetodo())
                        .ventaId(p.getVenta().getId())
                        .build())
                .toList();
    }

    @Override
    public List<PagoResponseDTO> obtenerPagoPorVenta(Long ventaId) {
        return pagoRepo.findByVentaId(ventaId)
                .stream()
                .map(pago -> PagoResponseDTO.builder()
                        .id(pago.getId())
                        .fecha(pago.getFecha())
                        .monto(pago.getMonto())
                        .metodo(pago.getMetodo())
                        .ventaId(pago.getVenta().getId())
                        .build())
                .toList();
    }

    @Override
    @Transactional
    public PagoResponseDTO actualizarPago(Long id, PagoRequestDTO request) {
        Pago pago = pagoRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Pago no encontrado"
                ));
        pago.setMonto(request.getMonto());
        pago.setMetodo(request.getMetodo());

        Pago pagoActualizado = pagoRepo.save(pago);

        return PagoResponseDTO.builder()
                .id(pagoActualizado.getId())
                .fecha(pagoActualizado.getFecha())
                .monto(pagoActualizado.getMonto())
                .metodo(pagoActualizado.getMetodo())
                .ventaId(pagoActualizado.getVenta().getId())
                .build();
    }

    @Override
    public void eliminarPago(Long id) {
        Pago pago = pagoRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pago no encontrado"));
        pagoRepo.delete(pago);
    }
}
