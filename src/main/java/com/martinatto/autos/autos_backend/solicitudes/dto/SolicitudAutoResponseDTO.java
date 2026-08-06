package com.martinatto.autos.autos_backend.solicitudes.dto;

import com.martinatto.autos.autos_backend.autos.enums.TipoCombustible;
import com.martinatto.autos.autos_backend.autos.enums.TipoTransmision;
import com.martinatto.autos.autos_backend.solicitudes.enums.EstadoSolicitud;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SolicitudAutoResponseDTO {
    private Long id;

    private String marca;
    private String modelo;

    private Integer anioDesde;
    private Integer anioHasta;

    private String colorPreferido;
    private Integer kilometrajeMaximo;
    private BigDecimal presupuestoMaximo;

    private TipoTransmision transmision;
    private TipoCombustible combustible;

    private EstadoSolicitud estadoSolicitud;
    private LocalDateTime fechaSolicitud;

    private Long clienteId;
}
