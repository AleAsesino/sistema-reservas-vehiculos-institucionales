package com.reservas.vehiculos.institucionales.model;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime fechaReserva;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;
}
