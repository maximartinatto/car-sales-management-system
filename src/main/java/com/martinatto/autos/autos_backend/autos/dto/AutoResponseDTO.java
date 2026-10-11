package com.martinatto.autos.autos_backend.autos.dto;

import com.martinatto.autos.autos_backend.autos.enums.EstadoAuto;
import com.martinatto.autos.autos_backend.autos.enums.TipoCombustible;
import com.martinatto.autos.autos_backend.autos.enums.TipoTransmision;
import com.martinatto.autos.autos_backend.autos.enums.TipoRodado;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AutoResponseDTO {

    private Long id;
    private String marca;
    private String modelo;
    private Integer anio;
    private String imagenUrl;
    private String color;
    private Integer kilometraje;
    private BigDecimal precio;
    private TipoRodado tipoRodado;

    private EstadoAuto estado;
    private TipoTransmision transmision;
    private TipoCombustible combustible;
}