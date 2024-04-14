package mx.edu.utez.server.modules.review.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.edu.utez.server.modules.doctor.model.Doctor;

import java.time.Instant;

@Entity
@Table(name = "reviews")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Review {
    public Review(String comment, Integer ranking, Boolean wasSkipped) {
        this.comment = comment;
        this.ranking = ranking;
        this.wasSkipped = wasSkipped;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String comment;

    @Column(columnDefinition = "TINYINT", nullable = false)
    private Integer ranking;

    @Column(columnDefinition = "TINYINT", nullable = false)
    private Boolean wasSkipped;

    @Column(columnDefinition = "DATETIME", nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Instant createdAt;

    // Relationships <-
    @ManyToOne
    @JoinColumn(name = "doctor_id", referencedColumnName = "id",
            nullable = false)
    @JsonIgnore
    private Doctor doctor;

    // Methods
    @PrePersist
    public void setCreatedAt() {
        this.createdAt = Instant.now();
    }
}
