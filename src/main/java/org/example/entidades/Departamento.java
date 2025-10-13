package org.example.entidades;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
@Table(name = "departamentos")
@Getter @Setter @ToString
@NoArgsConstructor
public class Departamento {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    @Enumerated(EnumType.STRING)
    private EspecialidadMedica especialidad;

    @ManyToOne(fetch = FetchType.LAZY)
    private Hospital hospital;

    @OneToMany(mappedBy = "departamento", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Medico> medicos = new ArrayList<>();

    @OneToMany(mappedBy = "departamento", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Sala> salas = new ArrayList<>();

    public Sala crearSala(String numero, String tipo) {
        Sala s = new Sala();
        s.setNumero(numero);
        s.setTipo(tipo);
        s.setDepartamento(this);
        salas.add(s);
        return s;
    }

    public void agregarMedico(Medico m) {
        medicos.add(m);
        m.setDepartamento(this);
    }

    public List<Medico> getMedicos() { return Collections.unmodifiableList(medicos); }
    public List<Sala> getSalas() { return Collections.unmodifiableList(salas); }
}
