package mx.edu.utez.server.modules.doctor.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
import mx.edu.utez.server.modules.review.model.Review;
import mx.edu.utez.server.modules.shift.model.Shift;
import mx.edu.utez.server.modules.speciality.model.Speciality;

import java.time.Instant;
import java.util.Set;

@Entity
@Table(name = "doctors")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Doctor {
    public Doctor(Long id, String professionalId, Long experience, Boolean isAux, String availableDays, Shift shift, Speciality speciality, Person person) {
        this.id = id;
        this.professionalId = professionalId;
        this.experience = experience;
        this.isAux = isAux;
        this.availableDays = availableDays;
        this.shift = shift;
        this.speciality = speciality;
        this.person = person;
    }

    public Doctor(String professionalId, Long experience, Boolean isAux, String availableDays, Shift shift, Speciality speciality, Person person) {
        this.professionalId = professionalId;
        this.experience = experience;
        this.isAux = isAux;
        this.availableDays = availableDays;
        this.shift = shift;
        this.speciality = speciality;
        this.person = person;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "VARCHAR(50)", nullable = false, unique = true)
    private String professionalId;

    @Column(columnDefinition = "TINYINT UNSIGNED", nullable = false)
    private Long experience;

    @Column(columnDefinition = "TINYINT", nullable = false)
    private Boolean isAux;

    @Column(columnDefinition = "VARCHAR(80)", nullable = false)
    private String availableDays;

    @Column(columnDefinition = "DATETIME", nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Instant createdAt;

    @Column(columnDefinition = "DATETIME", insertable = false)
    private Instant updatedAt;

    // Relationships <-
    @ManyToOne
    @JoinColumn(name = "shift_id", referencedColumnName = "id",
            nullable = false)
    @JsonIncludeProperties({"id", "name"})
    private Shift shift;

    @ManyToOne
    @JoinColumn(name = "speciality_id", referencedColumnName = "id",
            nullable = false)
    @JsonIncludeProperties({"id", "name"})
    private Speciality speciality;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "person_id", referencedColumnName = "id",
            nullable = false)
    private Person person;

    // Relationships ->
    @OneToMany(mappedBy = "doctor")
    @JsonIgnore
    private Set<Appointment> appointments;

    @OneToMany(mappedBy = "doctor")
    private Set<Review> reviews;

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
