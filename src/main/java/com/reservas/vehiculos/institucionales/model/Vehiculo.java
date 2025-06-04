package com.reservas.vehiculos.institucionales.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Vehiculo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String placa;

    private String marca;

    private String tipo;

    private String color;

    private int capacidad;

    private String estado;

    private String descripcion;

    private int cantAsientos;

    private LocalDateTime fechaRegistro;

    @OneToMany(mappedBy = "vehiculo")
    private List<Reparacion> reparaciones;

    @ManyToMany(mappedBy = "vehiculos")
    private List<Reserva> reservas;
}
