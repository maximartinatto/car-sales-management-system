package com.martinatto.autos.autos_backend.pagos.service;

import com.martinatto.autos.autos_backend.pagos.dto.PagoRequestDTO;
import com.martinatto.autos.autos_backend.pagos.dto.PagoResponseDTO;

import java.util.List;

public interface IPagoService {
    // Crear pago
    PagoResponseDTO crearPago(Long ventaId, PagoRequestDTO request);

    // Obtener todos los pagos
    List<PagoResponseDTO> obtenerTodos();

    // Obtener pago por venta
    List<PagoResponseDTO> obtenerPagoPorVenta(Long ventaId);



    // Actualizar pago
    PagoResponseDTO actualizarPago(Long id, PagoRequestDTO request);

    // Eliminar pago
    void eliminarPago(Long id);
}
