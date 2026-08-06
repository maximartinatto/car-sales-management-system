package com.martinatto.autos.autos_backend.solicitudes.mapper;

import com.martinatto.autos.autos_backend.solicitudes.dto.SolicitudAutoRequestDTO;
import com.martinatto.autos.autos_backend.solicitudes.dto.SolicitudAutoResponseDTO;
import com.martinatto.autos.autos_backend.solicitudes.entity.SolicitudAuto;
import com.martinatto.autos.autos_backend.solicitudes.enums.EstadoSolicitud;

import java.time.LocalDateTime;

public class SolicitudAutoMapper {

    public static SolicitudAuto toEntity(
            SolicitudAutoRequestDTO dto
    ) {
        return SolicitudAuto.builder()
                .marca(dto.getMarca())
                .modelo(dto.getModelo())
                .anioDesde(dto.getAnioDesde())
                .anioHasta(dto.getAnioHasta())
                .colorPreferido(dto.getColorPreferido())
                .kilometrajeMaximo(dto.getKilometrajeMaximo())
                .presupuestoMaximo(dto.getPresupuestoMaximo())
                .transmision(dto.getTransmision())
                .combustible(dto.getCombustible())
                .estadoSolicitud(EstadoSolicitud.PENDIENTE)
                .fechaSolicitud(LocalDateTime.now())
                .build();
    }

    public static SolicitudAutoResponseDTO toDTO(
            SolicitudAuto solicitud
    ) {
        return SolicitudAutoResponseDTO.builder()
                .id(solicitud.getId())
                .marca(solicitud.getMarca())
                .modelo(solicitud.getModelo())
                .anioDesde(solicitud.getAnioDesde())
                .anioHasta(solicitud.getAnioHasta())
                .colorPreferido(solicitud.getColorPreferido())
                .kilometrajeMaximo(solicitud.getKilometrajeMaximo())
                .presupuestoMaximo(solicitud.getPresupuestoMaximo())
                .transmision(solicitud.getTransmision())
                .combustible(solicitud.getCombustible())
                .estadoSolicitud(solicitud.getEstadoSolicitud())
                .fechaSolicitud(solicitud.getFechaSolicitud())
                .clienteId(
                        solicitud.getCliente() != null
                                ? solicitud.getCliente().getId()
                                : null
                )
                .build();
    }
}
