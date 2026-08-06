package com.martinatto.autos.autos_backend.pagos.controller;

import com.martinatto.autos.autos_backend.pagos.dto.PagoRequestDTO;
import com.martinatto.autos.autos_backend.pagos.dto.PagoResponseDTO;
import com.martinatto.autos.autos_backend.pagos.service.IPagoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pagos")
@RequiredArgsConstructor
public class PagoController {
    @Autowired
    private IPagoService pagoServ;

    @PostMapping("/venta/{ventaId}")
    public ResponseEntity<PagoResponseDTO> crarPago(
            @PathVariable Long ventaId,
            @Valid @RequestBody PagoRequestDTO request
            ) {
        return ResponseEntity.ok(pagoServ.crearPago(ventaId, request));
    }

    @GetMapping
    public ResponseEntity<List<PagoResponseDTO>> obtenerTodos() {
        return ResponseEntity.ok(pagoServ.obtenerTodos());
    }

    @GetMapping("/venta/{ventaId}")
    public ResponseEntity<List<PagoResponseDTO>> obtenerPagoPorVenta(
            @PathVariable Long ventaId
    ) {
        return ResponseEntity.ok(pagoServ.obtenerPagoPorVenta(ventaId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PagoResponseDTO> actualizarPago(
            @PathVariable Long id,
            @Valid @RequestBody PagoRequestDTO request
    ) {
        return ResponseEntity.ok(pagoServ.actualizarPago(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPago (@PathVariable Long id) {
        pagoServ.eliminarPago(id);
        return ResponseEntity.noContent().build();
    }
}
