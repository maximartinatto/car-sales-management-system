package com.martinatto.autos.autos_backend.clientes.service;

import com.martinatto.autos.autos_backend.clientes.dto.ClienteRequestDTO;
import com.martinatto.autos.autos_backend.clientes.dto.ClienteResponseDTO;
import com.martinatto.autos.autos_backend.clientes.entity.Cliente;
import com.martinatto.autos.autos_backend.clientes.mapper.ClienteMapper;
import com.martinatto.autos.autos_backend.clientes.repository.IClientesRepository;
import com.martinatto.autos.autos_backend.shared.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClienteService implements IClienteService {

    private final IClientesRepository clienteRepo;

    @Override
    @Transactional
    public ClienteResponseDTO crearCliente(ClienteRequestDTO request) {
        Cliente cliente = ClienteMapper.toEntity(request);
        Cliente clienteGuardado = clienteRepo.save(cliente);

        return ClienteMapper.toDTO(clienteGuardado);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ClienteResponseDTO> obtenerTodos() {
        return clienteRepo.findAll()
                .stream()
                .map(ClienteMapper::toDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public ClienteResponseDTO obtenerClientePorId(Long id) {
        Cliente cliente = clienteRepo.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Cliente no encontrado")
                );

        return ClienteMapper.toDTO(cliente);
    }

    @Override
    @Transactional
    public ClienteResponseDTO actualizarCliente(
            Long id,
            ClienteRequestDTO request
    ) {
        Cliente cliente = clienteRepo.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Cliente no encontrado")
                );

        cliente.setNombre(request.getNombre());
        cliente.setApellido(request.getApellido());
        cliente.setDni(request.getDni());
        cliente.setTelefono(request.getTelefono());
        cliente.setEmail(request.getEmail());

        Cliente clienteActualizado = clienteRepo.save(cliente);

        return ClienteMapper.toDTO(clienteActualizado);
    }

    @Override
    @Transactional
    public void eliminarCliente(Long id) {
        Cliente cliente = clienteRepo.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Cliente no encontrado")
                );

        clienteRepo.delete(cliente);
    }
}
