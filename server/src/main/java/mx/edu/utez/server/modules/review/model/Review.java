package mx.edu.utez.server.modules.review.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.edu.utez.server.modules.doctor.model.Doctor;
import mx.edu.utez.server.modules.status.model.Status;

@Entity
@Table(name = "reviews")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String comment;

    @Column(columnDefinition = "TINYINT", nullable = false)
    private Integer ranking;

    @Column(columnDefinition = "TINYINT", nullable = false)
    private Boolean wasSkipped;

    // Relationships <-
    @ManyToOne
    @JoinColumn(name = "doctor_id", referencedColumnName = "id",
            nullable = false)
    @JsonIgnoreProperties({"reviews"})
    private Doctor doctor;
}
