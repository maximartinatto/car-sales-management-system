package com.martinatto.autos.autos_backend.solicitudes.entity;

import com.martinatto.autos.autos_backend.autos.enums.TipoCombustible;
import com.martinatto.autos.autos_backend.autos.enums.TipoTransmision;
import com.martinatto.autos.autos_backend.clientes.entity.Cliente;
import com.martinatto.autos.autos_backend.solicitudes.enums.EstadoSolicitud;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "solicitudes_autos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SolicitudAuto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String marca;

    private String modelo;

    private Integer anioDesde;

    private Integer anioHasta;

    private String colorPreferido;

    private Integer kilometrajeMaximo;

    private BigDecimal presupuestoMaximo;

    @Enumerated(EnumType.STRING)
    private TipoTransmision transmision;

    @Enumerated(EnumType.STRING)
    private TipoCombustible combustible;

    @Enumerated(EnumType.STRING)
    private EstadoSolicitud estadoSolicitud;

    private LocalDateTime fechaSolicitud;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

}
