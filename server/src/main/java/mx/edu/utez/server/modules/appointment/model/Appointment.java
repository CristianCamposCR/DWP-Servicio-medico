package mx.edu.utez.server.modules.appointment.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
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
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.edu.utez.server.modules.appointment_type.model.AppointmentType;
import mx.edu.utez.server.modules.cancellation_reason.module.CancellationReason;
import mx.edu.utez.server.modules.doctor.model.Doctor;
import mx.edu.utez.server.modules.patient.model.Patient;
import mx.edu.utez.server.modules.payment.module.Payment;
import mx.edu.utez.server.modules.record.model.Record;
import mx.edu.utez.server.modules.shift.model.Shift;
import mx.edu.utez.server.modules.speciality.model.Speciality;
import mx.edu.utez.server.modules.status.model.Status;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "appointments")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Appointment {
    public Appointment(LocalDate scheduledAt, Patient patient, Speciality speciality, AppointmentType appointmentType, Shift preferentialShift) {
        this.scheduledAt = scheduledAt;
        this.patient = patient;
        this.speciality = speciality;
        this.appointmentType = appointmentType;
        this.preferentialShift = preferentialShift;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "VARCHAR(20)", nullable = false)
    private String folio;

    @Column(columnDefinition = "TINYINT UNSIGNED DEFAULT 60")
    private Long duration;

    @Column(columnDefinition = "TINYINT DEFAULT 0")
    private Boolean hasReview;

    @Column(nullable = false)
    private LocalDate scheduledAt;

    @Column(columnDefinition = "TINYINT UNSIGNED")
    private Integer scheduledHour;

    @Column(columnDefinition = "TINYINT DEFAULT 1")
    private Integer remainingReschedules;

    @Column(columnDefinition = "DATETIME", nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Instant createdAt;

    @Column(columnDefinition = "DATETIME", insertable = false)
    private Instant updatedAt;

    // Relationships <-
    @ManyToOne
    @JoinColumn(name = "status_id", referencedColumnName = "id",
            nullable = false)
    private Status status;

    @ManyToOne
    @JoinColumn(name = "patient_id", referencedColumnName = "id",
            nullable = false)
    @JsonIncludeProperties({"id", "patientCode", "person"})
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "doctor_id", referencedColumnName = "id")
    @JsonIncludeProperties({"id", "professionalId", "person"})
    private Doctor doctor;

    @ManyToOne
    @JoinColumn(name = "speciality_id", referencedColumnName = "id",
            nullable = false)
    @JsonIncludeProperties({"id", "name"})
    private Speciality speciality;

    @ManyToOne
    @JoinColumn(name = "appointment_type_id", referencedColumnName = "id",
            nullable = false)
    @JsonIncludeProperties({"id", "name"})
    private AppointmentType appointmentType;

    @ManyToOne
    @JoinColumn(name = "shift_id", referencedColumnName = "id",
            nullable = false)
    @JsonIncludeProperties({"id", "name"})
    private Shift preferentialShift;

    @ManyToMany
    @JoinTable(
            name = "appointment_cancellations",
            joinColumns = @JoinColumn(name = "appointment_id"),
            inverseJoinColumns = @JoinColumn(name = "cancellation_reason_id"))
    @JsonIgnore
    Set<CancellationReason> cancellationReasons;

    // Relationships ->
    @OneToOne(mappedBy = "appointment")
    @JsonIgnore
    private Record records;

    @OneToOne(mappedBy = "appointment")
    @JsonIgnore
    private Payment payment;

    // Methods
    @PrePersist
    public void setDefaultValues() {
        this.createdAt = Instant.now();
        this.duration = 60L;
        this.hasReview = false;
        this.remainingReschedules = 1;
    }

    @PreUpdate
    public void setUpdatedAt() {
        this.updatedAt = Instant.now();
    }
}
