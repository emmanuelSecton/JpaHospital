package org.example.entidades;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "salas")
@Getter @Setter @ToString
@NoArgsConstructor
public class Sala {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String numero;
    private String tipo;

    @ManyToOne(fetch = FetchType.LAZY)
    private Departamento departamento;

    @OneToMany(mappedBy = "sala", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Cita> citas = new ArrayList<>();

    public void agregarCita(Cita c) {
        citas.add(c);
        c.setSala(this);
    }
}
