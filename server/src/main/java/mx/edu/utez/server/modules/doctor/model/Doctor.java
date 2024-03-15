package mx.edu.utez.server.modules.doctor.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
import mx.edu.utez.server.modules.speciality.model.Speciality;
import mx.edu.utez.server.modules.status.model.Status;

import java.time.Instant;
import java.util.Set;

@Entity
@Table(name = "doctors")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "VARCHAR(20)", nullable = false, unique = true)
    private String professionalId;

    @Column(columnDefinition = "BIGINT UNSIGNED", nullable = false)
    private Long experience;

    @Column(nullable = false, insertable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Instant createdAt;

    // Relationships <-
    @ManyToOne
    @JoinColumn(name = "speciality_id", referencedColumnName = "id",
            nullable = false)
    private Speciality speciality;

    @ManyToOne
    @JoinColumn(name = "status_id", referencedColumnName = "id",
            nullable = false)
    private Status status;

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
}
