package org.example.entidades;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "citas")
@Getter @Setter @ToString
@NoArgsConstructor
public class Cita {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Paciente paciente;

    @ManyToOne(fetch = FetchType.LAZY)
    private Medico medico;

    @ManyToOne(fetch = FetchType.LAZY)
    private Sala sala;

    private LocalDateTime fechaHora;

    @Enumerated(EnumType.STRING)
    private EstadoCita estado = EstadoCita.PROGRAMADA;

    private BigDecimal costo;

    private String observaciones;
}
