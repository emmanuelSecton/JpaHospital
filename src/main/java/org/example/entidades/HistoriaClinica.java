package org.example.entidades;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "historias_clinicas", uniqueConstraints = @UniqueConstraint(columnNames = {"paciente_id"}))
@Getter @ToString
@NoArgsConstructor
public class HistoriaClinica {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "paciente_id", nullable = false, unique = true)
    private Paciente paciente;

    private String numeroHistoria;

    @ElementCollection
    @CollectionTable(name = "diagnosticos", joinColumns = @JoinColumn(name = "historia_id"))
    private List<String> diagnosticos = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "tratamientos", joinColumns = @JoinColumn(name = "historia_id"))
    private List<String> tratamientos = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "alergias", joinColumns = @JoinColumn(name = "historia_id"))
    private List<String> alergias = new ArrayList<>();

    private Instant fechaCreacion = Instant.now();

    public HistoriaClinica(Paciente paciente) {
        this.paciente = paciente;
        this.numeroHistoria = "HC-" + paciente.getDni() + "-" + Instant.now().toEpochMilli();
    }

    public void agregarDiagnostico(String d) { diagnosticos.add(d); }
    public void agregarTratamiento(String t) { tratamientos.add(t); }
    public void agregarAlergia(String a) { alergias.add(a); }
}
