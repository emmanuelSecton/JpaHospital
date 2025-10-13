package org.example.entidades;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.Builder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
@Table(name = "hospitales")
@Getter @Setter @ToString
@NoArgsConstructor
public class Hospital {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String direccion;
    private String telefono;

    @OneToMany(mappedBy = "hospital", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Departamento> departamentos = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Paciente> pacientes = new ArrayList<>();

    public void agregarDepartamento(Departamento d) {
        departamentos.add(d);
        d.setHospital(this);
    }

    public List<Departamento> getDepartamentos() {
        return Collections.unmodifiableList(departamentos);
    }

    public void agregarPaciente(Paciente p) {
        pacientes.add(p);
        p.setHospital(this);
    }
}
