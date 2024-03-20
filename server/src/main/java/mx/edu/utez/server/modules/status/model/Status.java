package mx.edu.utez.server.modules.status.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.edu.utez.server.kernel.StatusType;
import mx.edu.utez.server.modules.appointment.model.Appointment;
import mx.edu.utez.server.modules.appointmentType.model.AppointmentType;
import mx.edu.utez.server.modules.area.model.Area;
import mx.edu.utez.server.modules.doctor.model.Doctor;
import mx.edu.utez.server.modules.gender.model.Gender;
import mx.edu.utez.server.modules.patient.model.Patient;
import mx.edu.utez.server.modules.role.model.Role;
import mx.edu.utez.server.modules.speciality.model.Speciality;
import mx.edu.utez.server.modules.user.model.User;

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
    @Enumerated(EnumType.STRING)
    private mx.edu.utez.server.kernel.Status name;

    @Column(columnDefinition = "VARCHAR(50)", nullable = false)
    @Enumerated(EnumType.STRING)
    private StatusType statusType;

    // Relationships ->
    @OneToMany(mappedBy = "status")
    @JsonIgnore
    private Set<Gender> genders;

    @OneToMany(mappedBy = "status")
    @JsonIgnore
    private Set<Speciality> specialities;

    @OneToMany(mappedBy = "status")
    @JsonIgnore
    private Set<Area> areas;

    @OneToMany(mappedBy = "status")
    @JsonIgnore
    private Set<Role> roles;

    @OneToMany(mappedBy = "status")
    @JsonIgnore
    private Set<User> users;

    @OneToMany(mappedBy = "status")
    @JsonIgnore
    private Set<Doctor> doctors;

    @OneToMany(mappedBy = "status")
    @JsonIgnore
    private Set<Patient> patients;

    @OneToMany(mappedBy = "status")
    @JsonIgnore
    private Set<AppointmentType> appointmentTypes;

    @OneToMany(mappedBy = "status")
    @JsonIgnore
    private Set<Appointment> appointments;
}
