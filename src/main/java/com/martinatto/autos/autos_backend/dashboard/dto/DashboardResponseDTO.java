package com.martinatto.autos.autos_backend.dashboard.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DashboardResponseDTO {
    private long totalAutos;
    private long autosDisponibles;
    private long autosVendidos;

    private long totalClientes;

    private long totalVentas;
    private long ventasFinalizadas;

    private BigDecimal totalVendido;
    private BigDecimal totalPagado;

    private long solicitudesPendientes;
}
