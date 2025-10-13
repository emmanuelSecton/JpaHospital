package org.example.entidades;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "pacientes")
@Getter @Setter @ToString(callSuper = true, exclude = "citas")
@NoArgsConstructor
@SuperBuilder
public class Paciente extends Persona {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String telefono;
    private String direccion;

    @OneToOne(mappedBy = "paciente", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private HistoriaClinica historiaClinica;

    @ManyToOne(fetch = FetchType.LAZY)
    private Hospital hospital;

    @OneToMany(mappedBy = "paciente", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Cita> citas = new ArrayList<>();

    @PostLoad @PostPersist
    public void ensureHistoria() {
        if (historiaClinica == null) {
            historiaClinica = new HistoriaClinica(this);
        }
    }

    public void agregarCita(Cita c) {
        citas.add(c);
        c.setPaciente(this);
    }
}
