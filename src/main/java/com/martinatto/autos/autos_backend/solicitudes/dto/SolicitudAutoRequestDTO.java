package com.martinatto.autos.autos_backend.solicitudes.dto;

import com.martinatto.autos.autos_backend.autos.enums.TipoCombustible;
import com.martinatto.autos.autos_backend.autos.enums.TipoTransmision;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SolicitudAutoRequestDTO {
    @NotBlank(message = "La marca es obligatoria")
    private String marca;

    private String modelo;

    @NotNull(message = "El año mínimo es obligatorio")
    @Min(value = 1900, message = "El año mínimo no puede ser anterior a 1900")
    private Integer anioDesde;

    @NotNull(message = "El año máximo es obligatorio")
    @Min(value = 1900, message = "El año máximo no puede ser anterior a 1900")
    private Integer anioHasta;

    private String colorPreferido;

    @PositiveOrZero(message = "El kilometraje máximo no puede ser negativo")
    private Integer kilometrajeMaximo;

    @NotNull(message = "El presupuesto máximo es obligatorio")
    @Positive(message = "El presupuesto debe ser mayor a cero")
    private BigDecimal presupuestoMaximo;

    private TipoTransmision transmision;

    private TipoCombustible combustible;

    @NotNull(message = "Debe indicar el cliente")
    private Long clienteId;
}
