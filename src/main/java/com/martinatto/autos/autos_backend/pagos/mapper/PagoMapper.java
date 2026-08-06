package com.martinatto.autos.autos_backend.pagos.mapper;

import com.martinatto.autos.autos_backend.pagos.dto.PagoRequestDTO;
import com.martinatto.autos.autos_backend.pagos.dto.PagoResponseDTO;
import com.martinatto.autos.autos_backend.pagos.entity.Pago;


import java.time.LocalDateTime;

public class PagoMapper {
    public static Pago toEntity(PagoRequestDTO dto) {
        return Pago.builder()
                .monto(dto.getMonto())
                .metodo(dto.getMetodo())
                .fecha(LocalDateTime.now())
                .build();
    }

    public static PagoResponseDTO toDTO(Pago pago) {
        return PagoResponseDTO.builder()
                .id(pago.getId())
                .fecha(pago.getFecha())
                .monto(pago.getMonto())
                .metodo(pago.getMetodo())
                .ventaId(pago.getVenta() != null
                        ? pago.getVenta().getId()
                        : null)
                .build();

    }
}
