package com.martinatto.autos.autos_backend.ventas.service;

import com.martinatto.autos.autos_backend.ventas.dto.VentaRequestDTO;
import com.martinatto.autos.autos_backend.ventas.dto.VentaResponseDTO;
import com.martinatto.autos.autos_backend.ventas.enums.EstadoVenta;

import java.time.LocalDate;
import java.util.List;

public interface IVentaService {
    // Crear venta
    VentaResponseDTO crearVenta(VentaRequestDTO ventaDTO);

    // Obtener todas las ventas
    List<VentaResponseDTO> obtenerVentas();

    // Obtener una venta
    VentaResponseDTO obtenerVentaPorId(Long id);

    // Actualizar venta
    VentaResponseDTO actualizarVenta(Long id, VentaRequestDTO ventaDTO);

    // Eliminar venta
    void eliminarVenta(Long id);

    // Obtener venta por cliente
    List<VentaResponseDTO> obtenerVentasPorCliente(Long clienteId);

    // Obtener venta por estado
    List<VentaResponseDTO> obtenerVentasPorEstado(
            EstadoVenta estadoVenta
    );

    // Obtener venta por fecha
    List<VentaResponseDTO> obtenerVentasPorFecha(
            LocalDate fechaDesde,
            LocalDate fechaHasta
    );


}
