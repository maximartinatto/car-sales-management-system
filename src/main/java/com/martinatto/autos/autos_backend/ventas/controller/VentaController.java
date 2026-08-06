package com.martinatto.autos.autos_backend.ventas.controller;

import com.martinatto.autos.autos_backend.ventas.dto.VentaRequestDTO;
import com.martinatto.autos.autos_backend.ventas.dto.VentaResponseDTO;
import com.martinatto.autos.autos_backend.ventas.enums.EstadoVenta;
import com.martinatto.autos.autos_backend.ventas.service.IVentaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/ventas")
@RequiredArgsConstructor
public class VentaController {

    private final IVentaService ventaServ;

    @PostMapping
    public ResponseEntity<VentaResponseDTO> crearVenta(@Valid @RequestBody VentaRequestDTO request) {
        VentaResponseDTO ventaCreada = ventaServ.crearVenta(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ventaCreada);
    }

    @GetMapping
    public ResponseEntity<List<VentaResponseDTO>> listarVentas() {
        return ResponseEntity.ok(ventaServ.obtenerVentas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<VentaResponseDTO> obtenerVentaPorId(@PathVariable Long id) {
        return ResponseEntity.ok(ventaServ.obtenerVentaPorId(id));
    }

    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<VentaResponseDTO>> obtenerPorCliente(
            @PathVariable Long clienteId
    ) {
        return ResponseEntity.ok(
                ventaServ.obtenerVentasPorCliente(clienteId)
        );
    }

    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<VentaResponseDTO>> obtenerPorEstado(
            @PathVariable EstadoVenta estadoVenta
    ) {
        return ResponseEntity.ok(
                ventaServ.obtenerVentasPorEstado(estadoVenta)
        );
    }

    @GetMapping("/fecha")
    public ResponseEntity<List<VentaResponseDTO>> obtenerPorFecha(
            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate desde,

            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate hasta
    ) {
        return ResponseEntity.ok(
                ventaServ.obtenerVentasPorFecha(desde, hasta)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<VentaResponseDTO> actualizarVenta(
            @PathVariable Long id,
            @Valid @RequestBody VentaRequestDTO ventaDTO) {
        return ResponseEntity.ok(ventaServ.actualizarVenta(id, ventaDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarVenta(@PathVariable Long id) {
        ventaServ.eliminarVenta(id);
        return ResponseEntity.noContent().build();
    }

}
