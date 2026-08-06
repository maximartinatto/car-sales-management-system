package com.martinatto.autos.autos_backend.ventas.mapper;

import com.martinatto.autos.autos_backend.pagos.entity.Pago;
import com.martinatto.autos.autos_backend.ventas.dto.VentaRequestDTO;
import com.martinatto.autos.autos_backend.ventas.dto.VentaResponseDTO;
import com.martinatto.autos.autos_backend.ventas.entity.Venta;

import java.time.LocalDateTime;

public class VentaMapper {

    public static Venta toEntity(VentaRequestDTO dto) {
        return Venta.builder()
                .precioFinal(dto.getPrecioFinal())
                .estado(dto.getEstado())
                .fecha(LocalDateTime.now())
                .build();
    }

    public static VentaResponseDTO toDTO(Venta venta) {
        return VentaResponseDTO.builder()
                .id(venta.getId())
                .fecha(venta.getFecha())
                .precioFinal(venta.getPrecioFinal())
                .estado(venta.getEstado())
                .autoId(
                        venta.getAuto() != null
                                ? venta.getAuto().getId()
                                : null
                )
                .clienteId(
                        venta.getCliente() != null
                                ? venta.getCliente().getId()
                                : null
                )
                .pagosIds(
                        venta.getPagos() == null
                                ? null
                                : venta.getPagos()
                                .stream()
                                .map(Pago::getId)
                                .toList()
                )
                .build();
    }
}
