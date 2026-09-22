package com.martinatto.autos.autos_backend.autos.controller;

import com.martinatto.autos.autos_backend.autos.dto.AutoResponseDTO;
import com.martinatto.autos.autos_backend.autos.enums.EstadoAuto;
import com.martinatto.autos.autos_backend.autos.enums.TipoCombustible;
import com.martinatto.autos.autos_backend.autos.enums.TipoTransmision;
import com.martinatto.autos.autos_backend.autos.service.IAutoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/autos")
@RequiredArgsConstructor
public class AutoController {

    private final IAutoService autoServ;


    // =========================
    // CREAR AUTO
    // =========================

    @PostMapping(
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<AutoResponseDTO> crearAuto(

            @RequestParam String marca,

            @RequestParam String modelo,

            @RequestParam Integer anio,

            @RequestParam String color,

            @RequestParam Integer kilometraje,

            @RequestParam BigDecimal precio,

            @RequestParam EstadoAuto estado,

            @RequestParam TipoTransmision transmision,

            @RequestParam TipoCombustible combustible,

            @RequestPart("imagen") MultipartFile imagen

    ) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        autoServ.crearAuto(
                                marca,
                                modelo,
                                anio,
                                color,
                                kilometraje,
                                precio,
                                estado,
                                transmision,
                                combustible,
                                imagen
                        )
                );
    }


    // =========================
    // OBTENER TODOS
    // =========================

    @GetMapping
    public ResponseEntity<List<AutoResponseDTO>> listarAutos() {

        return ResponseEntity.ok(
                autoServ.obtenerTodos()
        );
    }


    // =========================
    // OBTENER POR ID
    // =========================

    @GetMapping("/{id}")
    public ResponseEntity<AutoResponseDTO> obtenerAutoPorId(
            @PathVariable Long id
    ) {

        return ResponseEntity.ok(
                autoServ.obtenerAutoPorId(id)
        );
    }


    // =========================
    // BUSCAR
    // =========================

    @GetMapping("/buscar")
    public ResponseEntity<List<AutoResponseDTO>> buscarAutos(
            @RequestParam String texto
    ) {

        return ResponseEntity.ok(
                autoServ.buscarAutos(texto)
        );
    }


    // =========================
    // FILTRAR POR ESTADO
    // =========================

    @GetMapping("/estado/{estadoAuto}")
    public ResponseEntity<List<AutoResponseDTO>> obtenerPorEstado(
            @PathVariable EstadoAuto estadoAuto
    ) {

        return ResponseEntity.ok(
                autoServ.obtenerPorEstado(estadoAuto)
        );
    }


    // =========================
    // FILTRAR POR MARCA
    // =========================

    @GetMapping("/marca/{marca}")
    public ResponseEntity<List<AutoResponseDTO>> obtenerPorMarca(
            @PathVariable String marca
    ) {

        return ResponseEntity.ok(
                autoServ.obtenerPorMarca(marca)
        );
    }


    // =========================
    // FILTRAR POR COMBUSTIBLE
    // =========================

    @GetMapping("/combustible/{combustible}")
    public ResponseEntity<List<AutoResponseDTO>> obtenerPorCombustible(
            @PathVariable TipoCombustible combustible
    ) {

        return ResponseEntity.ok(
                autoServ.obtenerPorCombustible(combustible)
        );
    }


    // =========================
    // FILTRAR POR TRANSMISIÓN
    // =========================

    @GetMapping("/transmision/{transmision}")
    public ResponseEntity<List<AutoResponseDTO>> obtenerPorTransmision(
            @PathVariable TipoTransmision transmision
    ) {

        return ResponseEntity.ok(
                autoServ.obtenerPorTransmision(transmision)
        );
    }


    // =========================
    // EDITAR AUTO
    // =========================

    @PutMapping(
            value = "/{id}",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<AutoResponseDTO> actualizarAuto(

            @PathVariable Long id,

            @RequestParam String marca,

            @RequestParam String modelo,

            @RequestParam Integer anio,

            @RequestParam String color,

            @RequestParam Integer kilometraje,

            @RequestParam BigDecimal precio,

            @RequestParam EstadoAuto estado,

            @RequestParam TipoTransmision transmision,

            @RequestParam TipoCombustible combustible,

            @RequestPart(
                    value = "imagen",
                    required = false
            )
            MultipartFile imagen

    ) {

        return ResponseEntity.ok(
                autoServ.actualizarAuto(
                        id,
                        marca,
                        modelo,
                        anio,
                        color,
                        kilometraje,
                        precio,
                        estado,
                        transmision,
                        combustible,
                        imagen
                )
        );
    }


    // =========================
    // ELIMINAR AUTO
    // =========================

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarAuto(
            @PathVariable Long id
    ) {

        autoServ.eliminarAuto(id);

        return ResponseEntity.noContent().build();
    }
}