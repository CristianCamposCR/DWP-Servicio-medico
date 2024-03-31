package mx.edu.utez.server.modules.person.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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
import mx.edu.utez.server.modules.gender.model.Gender;
import mx.edu.utez.server.modules.patient.model.Patient;
import mx.edu.utez.server.modules.user.model.User;

import java.time.LocalDate;

@Entity
@Table(name = "people")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Person {
    public Person(String name, String surname, String lastname, String email, String curp, String phoneNumber, LocalDate birthday, Gender gender) {
        this.name = name;
        this.surname = surname;
        this.lastname = lastname;
        this.email = email;
        this.curp = curp;
        this.phoneNumber = phoneNumber;
        this.birthday = birthday;
        this.gender = gender;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "VARCHAR(50)", nullable = false)
    private String name;

    @Column(columnDefinition = "VARCHAR(50)", nullable = false)
    private String surname;

    @Column(columnDefinition = "VARCHAR(50)")
    private String lastname;

    @Column(columnDefinition = "VARCHAR(100)", nullable = false)
    private String email;

    @Column(columnDefinition = "VARCHAR(18)", unique = true)
    private String curp;

    @Column(columnDefinition = "VARCHAR(15)", nullable = false)
    private String phoneNumber;

    @Column(columnDefinition = "TEXT")
    private String details;

    @Column(columnDefinition = "DATE", nullable = false)
    private LocalDate birthday;

    @Column(columnDefinition = "TEXT")
    private String profilePhoto;

    // Relationships <-
    @ManyToOne
    @JoinColumn(name = "gender_id", referencedColumnName = "id",
            nullable = false)
    private Gender gender;

    // Relationships ->
    @OneToOne(mappedBy = "person")
    @JsonIgnore
    private Doctor doctor;

    @OneToOne(mappedBy = "person")
    @JsonIgnore
    private Patient patient;

    @OneToOne(fetch = FetchType.EAGER,
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE},
            mappedBy = "person")
    @JsonIgnoreProperties({"person"})
    private User user;
}
