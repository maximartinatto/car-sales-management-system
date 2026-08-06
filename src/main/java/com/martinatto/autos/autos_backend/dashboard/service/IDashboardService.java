package com.martinatto.autos.autos_backend.dashboard.service;

import com.martinatto.autos.autos_backend.dashboard.dto.DashboardResponseDTO;
import com.martinatto.autos.autos_backend.dashboard.dto.VentaMensualDTO;

import java.util.List;

public interface IDashboardService {
    DashboardResponseDTO obtenerResumen();

    List<VentaMensualDTO> obtenerVentasMensuales();
}
