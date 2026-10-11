package com.martinatto.autos.autos_backend.solicitudes.dto;


import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SolicitudPublicaRequestDTO {
    @Valid
    @NotNull(message = "Los datos del cliente son obligatorios")
    private DatosClienteDTO cliente;

    @Valid
    @NotNull(message = "Los datos del vehículo son obligatorios")
    private SolicitudVehiculoPublicaDTO solicitud;
}
