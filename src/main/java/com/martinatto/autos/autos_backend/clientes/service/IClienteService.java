package com.martinatto.autos.autos_backend.clientes.service;

import com.martinatto.autos.autos_backend.clientes.dto.ClienteRequestDTO;
import com.martinatto.autos.autos_backend.clientes.dto.ClienteResponseDTO;

import java.util.List;

public interface IClienteService {
    // creamos un cliente
    ClienteResponseDTO crearCliente(ClienteRequestDTO request);

    // Obtenemos todos los clientes
    List<ClienteResponseDTO> obtenerTodos();

    // Obtenemos un cliente
    ClienteResponseDTO obtenerClientePorId(Long id);

    // Actualizamos un cliente
    ClienteResponseDTO actualizarCliente(Long id, ClienteRequestDTO request);

    // Eliminamos un cliente
    void eliminarCliente(Long id);
}
