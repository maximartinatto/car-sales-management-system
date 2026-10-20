
package com.martinatto.autos.autos_backend.pagos.service;

import com.martinatto.autos.autos_backend.pagos.dto.PagoRequestDTO;
import com.martinatto.autos.autos_backend.pagos.dto.PagoResponseDTO;
import com.martinatto.autos.autos_backend.pagos.entity.Pago;
import com.martinatto.autos.autos_backend.pagos.repository.IPagoRepository;
import com.martinatto.autos.autos_backend.shared.exception.ResourceNotFoundException;
import com.martinatto.autos.autos_backend.ventas.entity.Venta;
import com.martinatto.autos.autos_backend.ventas.enums.EstadoVenta;
import com.martinatto.autos.autos_backend.ventas.repository.IVentaRepository;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PagoService implements IPagoService {

    private final IPagoRepository pagoRepo;
    private final IVentaRepository ventaRepo;

    @Override
    @Transactional
    public PagoResponseDTO crearPago(
            Long ventaId,
            @Valid PagoRequestDTO request
    ) {
        Venta venta = ventaRepo.findById(ventaId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Venta no encontrada")
                );

        validarVenta(venta);

        validarMonto(request.getMonto());

        BigDecimal totalPagado =
                pagoRepo.sumarPagosPorVenta(ventaId);

        BigDecimal saldoPendiente =
                venta.getPrecioFinal().subtract(totalPagado);

        if (request.getMonto().compareTo(saldoPendiente) > 0) {
            throw new IllegalStateException(
                    "El monto supera el saldo pendiente de la venta."
            );
        }

        Pago pago = new Pago();
        pago.setFecha(LocalDateTime.now());
        pago.setMonto(request.getMonto());
        pago.setMetodo(request.getMetodo());
        pago.setVenta(venta);

        Pago guardado = pagoRepo.save(pago);

        return convertirADTO(guardado);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PagoResponseDTO> obtenerTodos() {
        return pagoRepo.findAll()
                .stream()
                .map(this::convertirADTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<PagoResponseDTO> obtenerPagoPorVenta(Long ventaId) {
        if (!ventaRepo.existsById(ventaId)) {
            throw new ResourceNotFoundException(
                    "Venta no encontrada"
            );
        }

        return pagoRepo.findByVentaId(ventaId)
                .stream()
                .map(this::convertirADTO)
                .toList();
    }

    @Override
    @Transactional
    public PagoResponseDTO actualizarPago(
            Long id,
            @Valid PagoRequestDTO request
    ) {
        Pago pago = pagoRepo.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Pago no encontrado")
                );

        Venta venta = pago.getVenta();

        validarVenta(venta);
        validarMonto(request.getMonto());

        BigDecimal totalPagado =
                pagoRepo.sumarPagosPorVenta(venta.getId());

        // Restamos el importe anterior para no contarlo dos veces.
        BigDecimal totalOtrosPagos =
                totalPagado.subtract(pago.getMonto());

        BigDecimal saldoDisponible =
                venta.getPrecioFinal().subtract(totalOtrosPagos);

        if (request.getMonto().compareTo(saldoDisponible) > 0) {
            throw new IllegalStateException(
                    "El nuevo monto supera el saldo disponible de la venta."
            );
        }

        pago.setMonto(request.getMonto());
        pago.setMetodo(request.getMetodo());

        Pago actualizado = pagoRepo.save(pago);

        return convertirADTO(actualizado);
    }

    @Override
    @Transactional
    public void eliminarPago(Long id) {
        Pago pago = pagoRepo.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Pago no encontrado")
                );

        validarVenta(pago.getVenta());

        pagoRepo.delete(pago);
    }

    // VALIDACIONES

    private void validarVenta(Venta venta) {
        if (venta.getEstado() == EstadoVenta.CANCELADA) {
            throw new IllegalStateException(
                    "No se pueden administrar pagos de una venta cancelada."
            );
        }
    }

    private void validarMonto(BigDecimal monto) {
        if (monto == null || monto.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalStateException(
                    "El monto del pago debe ser mayor a cero."
            );
        }
    }

    // CONVERSIÓN A DTO

    private PagoResponseDTO convertirADTO(Pago pago) {
        return PagoResponseDTO.builder()
                .id(pago.getId())
                .fecha(pago.getFecha())
                .monto(pago.getMonto())
                .metodo(pago.getMetodo())
                .ventaId(pago.getVenta().getId())
                .build();
    }
}
