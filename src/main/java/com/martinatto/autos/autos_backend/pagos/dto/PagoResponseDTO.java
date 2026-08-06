package com.martinatto.autos.autos_backend.pagos.dto;

import com.martinatto.autos.autos_backend.pagos.enums.MetodoPago;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PagoResponseDTO {
    private Long id;
    private LocalDateTime fecha;
    private BigDecimal monto;
    private MetodoPago metodo;
    private Long ventaId;
}
