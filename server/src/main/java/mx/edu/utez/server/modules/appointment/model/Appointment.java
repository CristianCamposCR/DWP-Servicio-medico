package mx.edu.utez.server.modules.appointment.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.edu.utez.server.modules.appointmentType.model.AppointmentType;
import mx.edu.utez.server.modules.cancellationReason.module.CancellationReason;
import mx.edu.utez.server.modules.doctor.model.Doctor;
import mx.edu.utez.server.modules.patient.model.Patient;
import mx.edu.utez.server.modules.record.model.Record;
import mx.edu.utez.server.modules.speciality.model.Speciality;
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

    @Column(columnDefinition = "TINYINT UNSIGNED", nullable = false)
    private Long duration;

    @Column(columnDefinition = "TINYINT", nullable = false)
    private Boolean hasReview;

    @Column(nullable = false, insertable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Instant createdAt;

    @Column(insertable = false)
    private Instant updatedAt;

    @Column(nullable = false)
    private Instant scheduledAt;

    @Column(columnDefinition = "TINYINT", nullable = false)
    private Integer remainingReschedules;

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
    @JoinColumn(name = "doctor_id", referencedColumnName = "id")
    private Doctor doctor;

    @ManyToOne
    @JoinColumn(name = "speciality_id", referencedColumnName = "id",
            nullable = false)
    private Speciality speciality;

    @ManyToOne
    @JoinColumn(name = "appointment_type_id", referencedColumnName = "id",
            nullable = false)
    private AppointmentType appointmentType;

    @ManyToMany
    @JoinTable(
            name = "appointment_cancellations",
            joinColumns = @JoinColumn(name = "appointment_id"),
            inverseJoinColumns = @JoinColumn(name = "cancellation_reason_id"))
    Set<CancellationReason> cancellationReasons;

    // Relationships ->
    @OneToOne(mappedBy = "appointment")
    @JsonIgnore
    private Record records;
}
