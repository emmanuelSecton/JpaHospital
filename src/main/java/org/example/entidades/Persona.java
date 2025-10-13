package org.example.entidades;

import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.time.Period;

@MappedSuperclass
@Getter @Setter @ToString
@NoArgsConstructor
@SuperBuilder
public abstract class Persona {
    @Column(nullable = false)
    protected String nombre;

    @Column(nullable = false)
    protected String apellido;

    @Column(nullable = false, unique = true)
    protected String dni;

    protected LocalDate fechaNacimiento;

    protected String tipoSangre;

    public Integer getEdad() {
        if (fechaNacimiento == null) return null;
        return Period.between(fechaNacimiento, LocalDate.now()).getYears();
    }
}
