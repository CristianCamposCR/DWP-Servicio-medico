package mx.edu.utez.server.modules.shift.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.edu.utez.server.kernel.Shifts;
import mx.edu.utez.server.modules.doctor.model.Doctor;
import mx.edu.utez.server.modules.status.model.Status;

import java.util.Set;

@Entity
@Table(name = "shifts")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Shift {
    public Shift(Shifts name, Integer entryHour, Integer departureHour, Status status) {
        this.name = name;
        this.entryHour = entryHour;
        this.departureHour = departureHour;
        this.status = status;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "VARCHAR(10)", nullable = false)
    @Enumerated(EnumType.STRING)
    private Shifts name;

    @Column(columnDefinition = "TINYINT UNSIGNED", nullable = false)
    private Integer entryHour;

    @Column(columnDefinition = "TINYINT UNSIGNED", nullable = false)
    private Integer departureHour;

    // Relationships <-
    @ManyToOne
    @JoinColumn(name = "status_id", referencedColumnName = "id",
            nullable = false)
    private Status status;

    // Relationships ->
    @OneToMany(mappedBy = "shift")
    @JsonIgnore
    private Set<Doctor> doctors;
}
