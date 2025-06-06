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

    private String nombre;

    @ManyToMany(mappedBy = "roles")
    @Builder.Default
    private Set<Usuario> usuarios = new HashSet<>();  // Inicialización del Set de usuarios
}
