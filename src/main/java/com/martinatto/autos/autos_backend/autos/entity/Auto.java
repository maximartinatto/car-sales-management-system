package com.martinatto.autos.autos_backend.autos.entity;

import com.martinatto.autos.autos_backend.autos.enums.EstadoAuto;
import com.martinatto.autos.autos_backend.autos.enums.TipoCombustible;
import com.martinatto.autos.autos_backend.autos.enums.TipoTransmision;
import com.martinatto.autos.autos_backend.ventas.entity.Venta;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "autos")
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class Auto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String marca;

    @Column(nullable = false)
    private String modelo;

    @Column(nullable = false)
    private Integer anio;

    @Column(length = 500)
    private String imagenUrl;

    private String color;

    private Integer Kilometraje;

    @Column(nullable = false)
    private BigDecimal precio;

    @Enumerated(EnumType.STRING)
    private EstadoAuto estado;

    @Enumerated(EnumType.STRING)
    private TipoTransmision transmision;

    @Enumerated(EnumType.STRING)
    private TipoCombustible combustible;

    @OneToMany(mappedBy = "auto")
    private List<Venta> ventas;
}
