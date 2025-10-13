package org.example.servicio;

import org.example.entidades.*;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class CitaManager {

    private static final Duration BUFFER = Duration.ofHours(2);

    public Cita programarCita(Paciente paciente, Medico medico, Sala sala, LocalDateTime fechaHora, BigDecimal costo) throws CitaException {
        if (fechaHora.isBefore(LocalDateTime.now())) {
            throw new CitaException("No se permiten citas en fechas pasadas");
        }
        if (costo == null || costo.compareTo(BigDecimal.ZERO) <= 0) {
            throw new CitaException("El costo debe ser mayor a cero");
        }
        if (medico.getEspecialidad() != sala.getDepartamento().getEspecialidad()) {
            throw new CitaException("La especialidad del médico no coincide con la del departamento/sala");
        }
        // disponibilidad medico
        List<Cita> mcitas = medico.getCitas();
        boolean medicoDisponible = mcitas.stream().noneMatch(c ->
                Math.abs(Duration.between(c.getFechaHora(), fechaHora).toMinutes()) < BUFFER.toMinutes()
        );
        if (!medicoDisponible) throw new CitaException("Médico no disponible en ese horario (buffer 2 horas)");
        // disponibilidad sala
        List<Cita> scitas = sala.getCitas();
        boolean salaDisponible = scitas.stream().noneMatch(c ->
                Math.abs(Duration.between(c.getFechaHora(), fechaHora).toMinutes()) < BUFFER.toMinutes()
        );
        if (!salaDisponible) throw new CitaException("Sala no disponible en ese horario (buffer 2 horas)");
        // crear cita
        Cita cita = new Cita();
        cita.setPaciente(paciente);
        cita.setMedico(medico);
        cita.setSala(sala);
        cita.setFechaHora(fechaHora);
        cita.setCosto(costo);
        // link
        paciente.agregarCita(cita);
        medico.agregarCita(cita);
        sala.agregarCita(cita);
        return cita;
    }
}
