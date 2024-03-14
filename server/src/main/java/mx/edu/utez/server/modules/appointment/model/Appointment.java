package mx.edu.utez.server.modules.appointment.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.edu.utez.server.modules.appointmentType.model.AppointmentType;
import mx.edu.utez.server.modules.doctor.model.Doctor;
import mx.edu.utez.server.modules.patient.model.Patient;
import mx.edu.utez.server.modules.record.model.Record;
import mx.edu.utez.server.modules.status.model.Status;

import java.time.Instant;
import java.util.Set;

@Entity
@Table(name = "appointments")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "VARCHAR(20)", nullable = false)
    private String folio;

    @Column(columnDefinition = "BIGINT UNSIGNED", nullable = false)
    private Long duration;

    @Column(columnDefinition = "TINYINT", nullable = false)
    private Boolean hasReview;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Instant createdAt;

    // Relationships <-
    @ManyToOne
    @JoinColumn(name = "status_id", referencedColumnName = "id",
            nullable = false)
    private Status status;

    @ManyToOne
    @JoinColumn(name = "patient_id", referencedColumnName = "id",
            nullable = false)
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "doctor_id", referencedColumnName = "id",
            nullable = false)
    private Doctor doctor;

    @ManyToOne
    @JoinColumn(name = "appointment_type_id", referencedColumnName = "id",
            nullable = false)
    private AppointmentType appointmentType;

    // Relationships ->
    @OneToMany(mappedBy = "appointment")
    @JsonIgnore
    private Set<Record> records;
}
