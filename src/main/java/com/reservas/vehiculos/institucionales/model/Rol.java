package com.reservas.vehiculos.institucionales.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Rol {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private NombreRol nombre;

    public enum NombreRol {
        ROL_USUARIO,
        ROL_INSPECTOR,
        ROL_ADMIN
    }


}
