package com.martinatto.autos.autos_backend.solicitudes.service;

import com.martinatto.autos.autos_backend.solicitudes.dto.EstadoSolicitudRequestDTO;
import com.martinatto.autos.autos_backend.solicitudes.dto.SolicitudAutoRequestDTO;
import com.martinatto.autos.autos_backend.solicitudes.dto.SolicitudAutoResponseDTO;
import com.martinatto.autos.autos_backend.solicitudes.enums.EstadoSolicitud;

import java.util.List;

public interface ISolicitudAutoService {
    SolicitudAutoResponseDTO crearSolicitud (SolicitudAutoRequestDTO requestDTO);

    List<SolicitudAutoResponseDTO> obtenerTodas();

    SolicitudAutoResponseDTO obtenerPorId(Long id);

    List<SolicitudAutoResponseDTO> obtenerPorCliente(Long clienteId);

    List<SolicitudAutoResponseDTO> obtenerPorEstado(EstadoSolicitud estadoSolicitud);

    SolicitudAutoResponseDTO actualizarSolicitud(Long id, SolicitudAutoRequestDTO requestDTO);

    SolicitudAutoResponseDTO actualizarEstado(Long id, EstadoSolicitudRequestDTO requestDTO);

    void eliminarSolicitud(Long id);
}
