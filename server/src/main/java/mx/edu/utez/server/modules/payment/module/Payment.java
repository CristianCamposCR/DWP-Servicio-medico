package mx.edu.utez.server.modules.payment.module;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.edu.utez.server.modules.appointment.model.Appointment;
import mx.edu.utez.server.modules.status.model.Status;

import java.time.Instant;

@Entity
@Table(name = "payments")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Payment {
    public Payment(Double total, Double totalPaid, Double discount, String charge) {
        this.total = total;
        this.totalPaid = totalPaid;
        this.discount = discount;
        this.charge = charge;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "DOUBLE", nullable = false)
    private Double total;

    @Column(columnDefinition = "DOUBLE", nullable = false)
    private Double totalPaid;

    @Column(columnDefinition = "DOUBLE", nullable = false)
    private Double discount;

    @Column(columnDefinition = "MEDIUMTEXT", nullable = false)
    private String charge;

    @Column(columnDefinition = "DATETIME", nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Instant createdAt;

    // Relationships <-
    @ManyToOne
    @JoinColumn(name = "status_id", referencedColumnName = "id",
            nullable = false)
    private Status status;

    @OneToOne
    @JoinColumn(name = "appointment_id", referencedColumnName = "id",
            nullable = false)
    private Appointment appointment;

    // Methods
    @PrePersist
    public void setCreatedAt() {
        this.createdAt = Instant.now();
    }
}
