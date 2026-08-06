package com.martinatto.autos.autos_backend.solicitudes.controller;

import com.martinatto.autos.autos_backend.solicitudes.dto.EstadoSolicitudRequestDTO;
import com.martinatto.autos.autos_backend.solicitudes.dto.SolicitudAutoRequestDTO;
import com.martinatto.autos.autos_backend.solicitudes.dto.SolicitudAutoResponseDTO;
import com.martinatto.autos.autos_backend.solicitudes.enums.EstadoSolicitud;
import com.martinatto.autos.autos_backend.solicitudes.service.ISolicitudAutoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/solicitudes-autos")
@RequiredArgsConstructor
public class SolicitudAutoController {

    private final ISolicitudAutoService solicitudService;

    @PostMapping
    public ResponseEntity<SolicitudAutoResponseDTO> crearSolicitud(
            @Valid @RequestBody SolicitudAutoRequestDTO request
    ) {
        SolicitudAutoResponseDTO solicitudCreada =
                solicitudService.crearSolicitud(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(solicitudCreada);
    }

    @GetMapping
    public ResponseEntity<List<SolicitudAutoResponseDTO>> obtenerTodas() {
        return ResponseEntity.ok(
                solicitudService.obtenerTodas()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<SolicitudAutoResponseDTO> obtenerPorId(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(
                solicitudService.obtenerPorId(id)
        );
    }

    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<SolicitudAutoResponseDTO>> obtenerPorCliente(
            @PathVariable Long clienteId
    ) {
        return ResponseEntity.ok(
                solicitudService.obtenerPorCliente(clienteId)
        );
    }

    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<SolicitudAutoResponseDTO>> obtenerPorEstado(
            @PathVariable EstadoSolicitud estado
    ) {
        return ResponseEntity.ok(
                solicitudService.obtenerPorEstado(estado)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<SolicitudAutoResponseDTO> actualizarSolicitud(
            @PathVariable Long id,
            @Valid @RequestBody SolicitudAutoRequestDTO request
    ) {
        return ResponseEntity.ok(
                solicitudService.actualizarSolicitud(id, request)
        );
    }

    @PatchMapping("/{id}/estado")
    public ResponseEntity<SolicitudAutoResponseDTO> actualizarEstado(
            @PathVariable Long id,
            @Valid @RequestBody EstadoSolicitudRequestDTO request
    ) {
        return ResponseEntity.ok(
                solicitudService.actualizarEstado(id, request)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarSolicitud(
            @PathVariable Long id
    ) {
        solicitudService.eliminarSolicitud(id);

        return ResponseEntity.noContent().build();
    }
}
