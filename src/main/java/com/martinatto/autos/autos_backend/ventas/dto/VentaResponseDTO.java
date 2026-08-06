package com.martinatto.autos.autos_backend.ventas.dto;

import com.martinatto.autos.autos_backend.ventas.enums.EstadoVenta;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VentaResponseDTO {

    private Long id;
    private LocalDateTime fecha;
    private BigDecimal precioFinal;
    private EstadoVenta estado;

    private Long autoId;
    private Long clienteId;

    private List<Long> pagosIds;
}