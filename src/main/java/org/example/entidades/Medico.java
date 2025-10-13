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
@Table(name = "medicos")
@Getter @Setter @ToString(callSuper = true, exclude = "citas")
@NoArgsConstructor
@SuperBuilder
public class Medico extends Persona {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private Matricula matricula;

    @Enumerated(EnumType.STRING)
    private EspecialidadMedica especialidad;

    @ManyToOne(fetch = FetchType.LAZY)
    private Departamento departamento;

    @OneToMany(mappedBy = "medico", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Cita> citas = new ArrayList<>();

    public void agregarCita(Cita c) {
        citas.add(c);
        c.setMedico(this);
    }
}
