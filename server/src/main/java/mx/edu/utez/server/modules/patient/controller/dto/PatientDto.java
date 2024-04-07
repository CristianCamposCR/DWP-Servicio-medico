package mx.edu.utez.server.modules.patient.controller.dto;

import lombok.Data;
import mx.edu.utez.server.modules.person.model.Person;
import mx.edu.utez.server.modules.status.model.Status;

import java.time.Instant;

@Data
public class PatientDto {
    private Long id;

    private String patientCode;

    private String cardNumber;

    private Instant createdAt;

    private Instant updatedAt;

    // Relationships <-
    private Status status;

    private Person person;
}
