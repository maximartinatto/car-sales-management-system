package com.martinatto.autos.autos_backend.clientes.entity;

import com.martinatto.autos.autos_backend.solicitudes.entity.SolicitudAuto;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "clientes")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String apellido;

    @Column(nullable = false, unique = true)
    private String dni;

    private String telefono;

    private String email;

    @OneToMany(mappedBy = "cliente")
    private List<SolicitudAuto> solicitudes;
}
