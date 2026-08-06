package com.martinatto.autos.autos_backend.dashboard.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VentaMensualDTO {
    private Integer anio;
    private Integer mes;
    private Long cantidadVentas;
    private BigDecimal totalVendido;
}
