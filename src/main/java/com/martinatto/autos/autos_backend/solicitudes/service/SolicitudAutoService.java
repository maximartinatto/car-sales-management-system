package com.martinatto.autos.autos_backend.solicitudes.service;

import com.martinatto.autos.autos_backend.clientes.entity.Cliente;
import com.martinatto.autos.autos_backend.clientes.repository.IClientesRepository;
import com.martinatto.autos.autos_backend.shared.exception.ResourceNotFoundException;
import com.martinatto.autos.autos_backend.solicitudes.dto.EstadoSolicitudRequestDTO;
import com.martinatto.autos.autos_backend.solicitudes.dto.SolicitudAutoRequestDTO;
import com.martinatto.autos.autos_backend.solicitudes.dto.SolicitudAutoResponseDTO;
import com.martinatto.autos.autos_backend.solicitudes.entity.SolicitudAuto;
import com.martinatto.autos.autos_backend.solicitudes.enums.EstadoSolicitud;
import com.martinatto.autos.autos_backend.solicitudes.mapper.SolicitudAutoMapper;
import com.martinatto.autos.autos_backend.solicitudes.dto.DatosClienteDTO;
import com.martinatto.autos.autos_backend.solicitudes.dto.SolicitudPublicaRequestDTO;
import com.martinatto.autos.autos_backend.solicitudes.dto.SolicitudVehiculoPublicaDTO;
import com.martinatto.autos.autos_backend.solicitudes.repository.ISolicitudAutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SolicitudAutoService implements ISolicitudAutoService{

    private final ISolicitudAutoRepository solicitudAutoRepo;
    private final IClientesRepository clientesRepo;

    @Override
    @Transactional
    public SolicitudAutoResponseDTO crearSolicitud(SolicitudAutoRequestDTO requestDTO) {
        validarRangoAnios(requestDTO);

        Cliente cliente = clientesRepo.findById(requestDTO.getClienteId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Cliente no encontrado"
                ));
        SolicitudAuto solicitudAuto = SolicitudAutoMapper.toEntity(requestDTO);
        solicitudAuto.setCliente(cliente);

        SolicitudAuto solicitudGuardada = solicitudAutoRepo.save(solicitudAuto);
        return SolicitudAutoMapper.toDTO(solicitudGuardada);
    }

    @Transactional
    public void crearSolicitudPublica(SolicitudPublicaRequestDTO request) {
        DatosClienteDTO datosCliente = request.getCliente();
        SolicitudVehiculoPublicaDTO datosSolicitud = request.getSolicitud();

        if (datosSolicitud.getAnioDesde() > datosSolicitud.getAnioHasta()) {
            throw new IllegalArgumentException(
                    "El año mínimo no puede ser mayor que el año máximo"
            );
        }

        String dni = datosCliente.getDni().trim();

        Cliente cliente = clientesRepo.findByDni(dni)
                .orElseGet(() -> {
                    Cliente nuevoCliente = Cliente.builder()
                            .nombre(datosCliente.getNombre())
                            .apellido(datosCliente.getApellido())
                            .dni(dni)
                            .telefono(datosCliente.getTelefono())
                            .email(datosCliente.getEmail())
                            .build();

                    return clientesRepo.save(nuevoCliente);
                });
        SolicitudAutoRequestDTO solicitudDTO =
                new SolicitudAutoRequestDTO();

        solicitudDTO.setMarca(datosSolicitud.getMarca());
        solicitudDTO.setModelo(datosSolicitud.getModelo());
        solicitudDTO.setAnioDesde(datosSolicitud.getAnioDesde());
        solicitudDTO.setAnioHasta(datosSolicitud.getAnioHasta());
        solicitudDTO.setColorPreferido(datosSolicitud.getColorPreferido());
        solicitudDTO.setKilometrajeMaximo(datosSolicitud.getKilometrajeMaximo());
        solicitudDTO.setPresupuestoMaximo(datosSolicitud.getPresupuestoMaximo());
        solicitudDTO.setTransmision(datosSolicitud.getTransmision());
        solicitudDTO.setCombustible(datosSolicitud.getCombustible());
        solicitudDTO.setClienteId(cliente.getId());

        crearSolicitud(solicitudDTO);

    }

    @Override
    @Transactional(readOnly = true)
    public List<SolicitudAutoResponseDTO> obtenerTodas() {
        return solicitudAutoRepo.findAll()
                .stream()
                .map(SolicitudAutoMapper::toDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public SolicitudAutoResponseDTO obtenerPorId(Long id) {
        SolicitudAuto solicitudAuto = buscarSolicitud(id);
        return SolicitudAutoMapper.toDTO(solicitudAuto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SolicitudAutoResponseDTO> obtenerPorCliente(Long clienteId) {
        if (!clientesRepo.existsById(clienteId)) {
            throw new ResourceNotFoundException(
                    "Cliente no encontrado"
            );
        }

        return solicitudAutoRepo.findByClienteId(clienteId)
                .stream()
                .map(SolicitudAutoMapper::toDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<SolicitudAutoResponseDTO> obtenerPorEstado(EstadoSolicitud estadoSolicitud) {
        return solicitudAutoRepo.findByEstadoSolicitud(estadoSolicitud)
                .stream()
                .map(SolicitudAutoMapper::toDTO)
                .toList();
    }

    @Override
    public SolicitudAutoResponseDTO actualizarSolicitud(Long id, SolicitudAutoRequestDTO requestDTO) {
        validarRangoAnios(requestDTO);

        SolicitudAuto solicitudAuto = buscarSolicitud(id);

        Cliente cliente = clientesRepo.findById(requestDTO.getClienteId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Cliente no encontrado"
                ));

        solicitudAuto.setMarca(requestDTO.getMarca());
        solicitudAuto.setModelo(requestDTO.getModelo());
        solicitudAuto.setAnioDesde(requestDTO.getAnioDesde());
        solicitudAuto.setAnioHasta(requestDTO.getAnioHasta());
        solicitudAuto.setColorPreferido(requestDTO.getColorPreferido());
        solicitudAuto.setKilometrajeMaximo(requestDTO.getKilometrajeMaximo());
        solicitudAuto.setPresupuestoMaximo(requestDTO.getPresupuestoMaximo());
        solicitudAuto.setTransmision(requestDTO.getTransmision());
        solicitudAuto.setCombustible(requestDTO.getCombustible());
        solicitudAuto.setCliente(cliente);

        SolicitudAuto solicitudActualizada = solicitudAutoRepo.save(solicitudAuto);
        return SolicitudAutoMapper.toDTO(solicitudActualizada);

    }

    @Override
    @Transactional
    public SolicitudAutoResponseDTO actualizarEstado(Long id, EstadoSolicitudRequestDTO requestDTO) {
        SolicitudAuto solicitud = buscarSolicitud(id);

        solicitud.setEstadoSolicitud(requestDTO.getEstadoSolicitud());
        SolicitudAuto solicitudActualizada = solicitudAutoRepo.save(solicitud);

        return SolicitudAutoMapper.toDTO(solicitudActualizada);
    }

    @Override
    @Transactional
    public void eliminarSolicitud(Long id) {
        SolicitudAuto solicitudAuto = buscarSolicitud(id);

        solicitudAutoRepo.delete(solicitudAuto);

    }

    private SolicitudAuto buscarSolicitud(Long id) {
        return solicitudAutoRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Solicitud de auto no encontrada"
                ));
    }

    private void validarRangoAnios(SolicitudAutoRequestDTO requestDTO) {
        if (requestDTO.getAnioDesde() > requestDTO.getAnioHasta()) {
            throw new IllegalStateException(
                    "El año mínimo no puede ser mayor que el año máximo"
            );
        }
    }
}
