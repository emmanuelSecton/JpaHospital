package org.example.entidades;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.ToString;

import java.io.Serializable;

@Embeddable
@Getter
@ToString
public class Matricula implements Serializable {
    private String numero;

    protected Matricula() {}

    public Matricula(String numero) {
        if (numero == null || !numero.matches("MP-\\\\\\d{4,6}")) {
            throw new IllegalArgumentException("Matrícula inválida, formato MP-XXXXX");
        }
        this.numero = numero;
    }
}
