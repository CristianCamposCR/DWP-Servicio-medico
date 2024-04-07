package mx.edu.utez.server.modules.speciality.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
import mx.edu.utez.server.modules.area.model.Area;
import mx.edu.utez.server.modules.doctor.model.Doctor;
import mx.edu.utez.server.modules.status.model.Status;

import java.time.Instant;
import java.util.Set;

@Entity
@Table(name = "specialities")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Speciality {
    public Speciality(Long id, String name, String description, Double cost, String bannerImage, Area area, Status status) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.cost = cost;
        this.bannerImage = bannerImage;
        this.area = area;
        this.status = status;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "VARCHAR(45)", nullable = false, unique = true)
    private String name;

    @Column(columnDefinition = "VARCHAR(200)")
    private String description;

    @Column(columnDefinition = "DOUBLE", nullable = false)
    private Double cost;

    @Column(columnDefinition = "MEDIUMTEXT")
    private String bannerImage;

    @Column(columnDefinition = "DATETIME", nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Instant createdAt;

    @Column(columnDefinition = "DATETIME", insertable = false)
    private Instant updatedAt;

    // Relationships <-
    @ManyToOne
    @JoinColumn(name = "area_id", referencedColumnName = "id",
            nullable = false)
    private Area area;

    @ManyToOne
    @JoinColumn(name = "status_id", referencedColumnName = "id",
            nullable = false)
    private Status status;

    // Relationships ->
    @OneToMany(mappedBy = "speciality")
    @JsonIgnore
    private Set<Doctor> doctors;

    @OneToMany(mappedBy = "speciality")
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
