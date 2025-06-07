package com.reservas.vehiculos.institucionales.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
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

    @NotBlank(message = "La marca es obligatoria")
    private String marca;

    @NotBlank(message = "El tipo es obligatorio")
    private String tipo;


    private String color;

    private int capacidad;

    private String estado;

    private String descripcion;

    private int cantAsientos;

    private LocalDateTime fechaRegistro;


    @JsonManagedReference
    @OneToMany(mappedBy = "vehiculo")
    private List<Reparacion> reparaciones;

    @ManyToMany(mappedBy = "vehiculos")
    @JsonManagedReference
    private List<Reserva> reservas;
}

