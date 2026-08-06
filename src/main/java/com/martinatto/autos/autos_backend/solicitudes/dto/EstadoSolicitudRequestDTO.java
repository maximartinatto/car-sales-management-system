package com.martinatto.autos.autos_backend.solicitudes.dto;

import com.martinatto.autos.autos_backend.solicitudes.enums.EstadoSolicitud;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EstadoSolicitudRequestDTO {
    @NotNull(message = "El estado de la solicitud es obligatorio")
    private EstadoSolicitud estadoSolicitud;
}
