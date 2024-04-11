package mx.edu.utez.server.modules.patient.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
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
import mx.edu.utez.server.modules.appointment.model.Appointment;
import mx.edu.utez.server.modules.person.model.Person;

import java.time.Instant;
import java.util.Set;

@Entity
@Table(name = "patients")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Patient {
    public Patient(String patientCode, Person person) {
        this.patientCode = patientCode;
        this.person = person;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "VARCHAR(20)", nullable = false, unique = true)
    private String patientCode;

    @Column(columnDefinition = "VARCHAR(200)")
    private String cardNumber;

    @Column(columnDefinition = "DATETIME", nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Instant createdAt;

    @Column(columnDefinition = "DATETIME", insertable = false)
    private Instant updatedAt;

    // Relationships <-
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "person_id", referencedColumnName = "id",
            nullable = false)
    private Person person;

    // Relationships ->
    @OneToMany(mappedBy = "patient")
    @JsonIgnore
    private Set<Appointment> appointments;

    // Methods
    @PrePersist
    public void setCreatedAt() {
        this.createdAt = Instant.now();
    }

    @PreUpdate
    public void setUpdatedAt() {
        this.updatedAt = Instant.now();
    }
}
