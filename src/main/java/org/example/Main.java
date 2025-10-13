package org.example;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.example.entidades.*;
import org.example.servicio.CitaManager;
import org.example.servicio.CitaException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hospital-persistence-unit");
        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();

        Hospital hospital = new Hospital();
        hospital.setNombre("Hospital Central");
        hospital.setDireccion("Av. Libertador 1234");
        hospital.setTelefono("011-4567-8901");

        Departamento cardiologia = new Departamento();
        cardiologia.setNombre("Cardiología");
        cardiologia.setEspecialidad(EspecialidadMedica.CARDIOLOGIA);

        hospital.agregarDepartamento(cardiologia);

        Sala consultorio = cardiologia.crearSala("CARD-101", "Consultorio");

        Medico cardiologo = new Medico();
        cardiologo.setNombre("Carlos");
        cardiologo.setApellido("González");
        cardiologo.setDni("12345678");
        cardiologo.setFechaNacimiento(LocalDate.of(1975,5,15));
        cardiologo.setTipoSangre(String.valueOf(TipoSangre.A_POSITIVO));
        cardiologo.setMatricula(new Matricula("MP-12345"));
        cardiologo.setEspecialidad(EspecialidadMedica.CARDIOLOGIA);

        cardiologia.agregarMedico(cardiologo);

        Paciente paciente = new Paciente();
        paciente.setNombre("María");
        paciente.setApellido("López");
        paciente.setDni("11111111");
        paciente.setFechaNacimiento(LocalDate.of(1985,12,5));
        paciente.setTipoSangre(String.valueOf(TipoSangre.A_POSITIVO));
        paciente.setTelefono("011-1111-1111");
        paciente.setDireccion("Calle Falsa 123");

        hospital.agregarPaciente(paciente);

        // Historia se crea automáticamente al persistir
        paciente.ensureHistoria();

        em.persist(hospital);
        em.persist(consultorio);
        em.persist(cardiologo);
        em.persist(paciente);

        em.getTransaction().commit();

        // programar cita
        CitaManager cm = new CitaManager();
        try {
            em.getTransaction().begin();
            Cita cita = cm.programarCita(paciente, cardiologo, consultorio, LocalDateTime.now().plusDays(1).withHour(10).withMinute(0), new BigDecimal("150000.00"));
            em.persist(cita);
            em.getTransaction().commit();
            System.out.println("Cita programada con id: " + cita.getId());
        } catch (CitaException e) {
            System.err.println("Error al programar cita: " + e.getMessage());
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
        }

        em.close();
        emf.close();
    }
}
