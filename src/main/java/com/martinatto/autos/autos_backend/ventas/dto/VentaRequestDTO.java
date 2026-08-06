package com.martinatto.autos.autos_backend.ventas.dto;

import com.martinatto.autos.autos_backend.ventas.enums.EstadoVenta;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VentaRequestDTO {

    @NotNull(message = "El precio final es obligatorio")
    @Positive(message = "El precio final debe ser mayor a cero")
    private BigDecimal precioFinal;

    @NotNull(message = "El estado de la venta es obligatorio")
    private EstadoVenta estado;

    @NotNull(message = "Debe indicar el auto")
    private Long autoId;

    @NotNull(message = "Debe indicar el cliente")
    private Long clienteId;
}
