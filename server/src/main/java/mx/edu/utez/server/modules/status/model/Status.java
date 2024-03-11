package mx.edu.utez.server.modules.status.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.edu.utez.server.modules.appointmentType.model.AppointmentType;
import mx.edu.utez.server.modules.area.model.Area;
import mx.edu.utez.server.modules.doctor.model.Doctor;
import mx.edu.utez.server.modules.genre.model.Genre;
import mx.edu.utez.server.modules.patient.model.Patient;
import mx.edu.utez.server.modules.person.model.Person;
import mx.edu.utez.server.modules.rol.model.Rol;
import mx.edu.utez.server.modules.speciality.model.Speciality;

import java.util.Set;

@Entity
@Table(name = "statuses")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Status {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "VARCHAR(45)", nullable = false, unique = true)
    private String name;

    // Relationships ->
    @OneToMany(mappedBy = "status")
    @JsonIgnore
    private Set<Genre> genres;

    @OneToMany(mappedBy = "status")
    @JsonIgnore
    private Set<Speciality> specialities;

    @OneToMany(mappedBy = "status")
    @JsonIgnore
    private Set<Area> areas;

    @OneToMany(mappedBy = "status")
    @JsonIgnore
    private Set<Rol> roles;

    @OneToMany(mappedBy = "status")
    @JsonIgnore
    private Set<Person> people;

    @OneToMany(mappedBy = "status")
    @JsonIgnore
    private Set<Doctor> doctors;

    @OneToMany(mappedBy = "status")
    @JsonIgnore
    private Set<Patient> patients;

    @OneToMany(mappedBy = "status")
    @JsonIgnore
    private Set<AppointmentType> appointmentTypes;
}
