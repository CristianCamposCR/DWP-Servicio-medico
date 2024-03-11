package mx.edu.utez.server.modules.person.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.edu.utez.server.modules.doctor.model.Doctor;
import mx.edu.utez.server.modules.genre.model.Genre;
import mx.edu.utez.server.modules.patient.model.Patient;
import mx.edu.utez.server.modules.status.model.Status;

import java.time.LocalDate;

@Entity
@Table(name = "people")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "VARCHAR(50)", nullable = false)
    private String name;

    @Column(columnDefinition = "VARCHAR(50)", nullable = false)
    private String surname;

    @Column(columnDefinition = "VARCHAR(50)")
    private String lastname;

    @Column(columnDefinition = "VARCHAR(18)", unique = true)
    private String curp;

    @Column(columnDefinition = "JSON")
    private String details;

    @Column(columnDefinition = "DATE")
    private LocalDate birthday;

    // Relationships <-
    @ManyToOne
    @JoinColumn(name = "genre_id", referencedColumnName = "id",
            nullable = false)
    private Genre genre;

    @ManyToOne
    @JoinColumn(name = "status_id", referencedColumnName = "id",
            nullable = false)
    private Status status;

    // Relationships ->
    @OneToOne(mappedBy = "person")
    @JsonIgnore
    private Doctor doctor;

    @OneToOne(mappedBy = "person")
    @JsonIgnore
    private Patient patient;
}
