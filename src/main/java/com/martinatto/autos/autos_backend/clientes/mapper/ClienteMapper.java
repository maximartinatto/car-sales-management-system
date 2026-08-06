package com.martinatto.autos.autos_backend.clientes.mapper;

import com.martinatto.autos.autos_backend.clientes.dto.ClienteRequestDTO;
import com.martinatto.autos.autos_backend.clientes.dto.ClienteResponseDTO;
import com.martinatto.autos.autos_backend.clientes.entity.Cliente;

public class ClienteMapper {
    public static Cliente toEntity(ClienteRequestDTO dto) {
        return Cliente.builder()
                .nombre(dto.getNombre())
                .apellido(dto.getApellido())
                .dni(dto.getDni())
                .telefono(dto.getTelefono())
                .email(dto.getEmail())
                .build();
    }

    public static ClienteResponseDTO toDTO(Cliente cliente) {
        return ClienteResponseDTO.builder()
                .id(cliente.getId())
                .nombre(cliente.getNombre())
                .apellido(cliente.getApellido())
                .dni(cliente.getDni())
                .telefono(cliente.getTelefono())
                .email(cliente.getEmail())
                .build();
    }
}
