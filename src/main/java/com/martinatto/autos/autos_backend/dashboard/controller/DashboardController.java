package com.martinatto.autos.autos_backend.dashboard.controller;

import com.martinatto.autos.autos_backend.dashboard.dto.DashboardResponseDTO;
import com.martinatto.autos.autos_backend.dashboard.dto.VentaMensualDTO;
import com.martinatto.autos.autos_backend.dashboard.service.IDashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
public class DashboardController {
    private final IDashboardService dashboardService;

    @GetMapping
    public ResponseEntity<DashboardResponseDTO> obtenerResumen() {
        return ResponseEntity.ok(
                dashboardService.obtenerResumen()
        );
    }

    @GetMapping("/ventas-mensuales")
    public ResponseEntity<List<VentaMensualDTO>> obtenerVentasMensuales() {
        return ResponseEntity.ok(
                dashboardService.obtenerVentasMensuales()
        );
    }
}
