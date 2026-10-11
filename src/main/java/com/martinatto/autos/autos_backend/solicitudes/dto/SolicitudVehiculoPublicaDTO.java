package com.martinatto.autos.autos_backend.solicitudes.dto;

import com.martinatto.autos.autos_backend.autos.enums.TipoCombustible;
import com.martinatto.autos.autos_backend.autos.enums.TipoTransmision;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class SolicitudVehiculoPublicaDTO {
    @NotBlank(message = "La marca es obligatoria")
    private String marca;

    private String modelo;

    @NotNull(message = "El año mínimo es obligatorio")
    @Min(1900)
    private Integer anioDesde;

    @NotNull(message = "El año máximo es obligatorio")
    @Min(1900)
    private Integer anioHasta;

    private String colorPreferido;

    @PositiveOrZero
    private Integer kilometrajeMaximo;

    @NotNull(message = "El presupuesto es obligatorio")
    @Positive
    private BigDecimal presupuestoMaximo;

    private TipoTransmision transmision;

    private TipoCombustible combustible;
}
