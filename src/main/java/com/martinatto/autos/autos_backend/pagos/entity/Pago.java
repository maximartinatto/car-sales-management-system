package com.martinatto.autos.autos_backend.pagos.entity;

import com.martinatto.autos.autos_backend.pagos.enums.MetodoPago;
import com.martinatto.autos.autos_backend.ventas.entity.Venta;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "pagos")
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Pago {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime fecha;

    private BigDecimal monto;

    @Enumerated(EnumType.STRING)
    private MetodoPago metodo;

    // Relacion

    @ManyToOne
    @JoinColumn(name = "venta_id", nullable = false)
    private Venta venta;
}
