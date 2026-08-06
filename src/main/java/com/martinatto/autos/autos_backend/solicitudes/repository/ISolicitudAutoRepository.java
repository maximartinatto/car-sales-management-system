package com.martinatto.autos.autos_backend.solicitudes.repository;

import com.martinatto.autos.autos_backend.solicitudes.entity.SolicitudAuto;
import com.martinatto.autos.autos_backend.solicitudes.enums.EstadoSolicitud;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ISolicitudAutoRepository
        extends JpaRepository<SolicitudAuto, Long> {

    List<SolicitudAuto> findByClienteId(Long clienteId);

    List<SolicitudAuto> findByEstadoSolicitud(
            EstadoSolicitud estadoSolicitud
    );

    Long countByEstadoSolicitud(EstadoSolicitud estadoSolicitud);
}
