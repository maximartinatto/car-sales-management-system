package com.martinatto.autos.autos_backend.clientes.controller;

import com.martinatto.autos.autos_backend.clientes.dto.ClienteRequestDTO;
import com.martinatto.autos.autos_backend.clientes.dto.ClienteResponseDTO;
import com.martinatto.autos.autos_backend.clientes.service.IClienteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clientes")
@RequiredArgsConstructor
public class ClienteController {

    private final IClienteService clienteServ;

    @PostMapping
    public ResponseEntity<ClienteResponseDTO> crearCliente(
            @Valid @RequestBody ClienteRequestDTO request
    ) {
        ClienteResponseDTO clienteCreado =
                clienteServ.crearCliente(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(clienteCreado);
    }

    @GetMapping
    public ResponseEntity<List<ClienteResponseDTO>> obtenerTodos() {
        return ResponseEntity.ok(clienteServ.obtenerTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteResponseDTO> obtenerPorId(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(
                clienteServ.obtenerClientePorId(id)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClienteResponseDTO> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody ClienteRequestDTO request
    ) {
        return ResponseEntity.ok(
                clienteServ.actualizarCliente(id, request)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        clienteServ.eliminarCliente(id);
        return ResponseEntity.noContent().build();
    }
}
