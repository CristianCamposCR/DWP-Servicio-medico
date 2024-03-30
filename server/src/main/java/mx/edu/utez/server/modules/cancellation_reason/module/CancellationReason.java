package mx.edu.utez.server.modules.cancellation_reason.module;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.edu.utez.server.modules.appointment.model.Appointment;
import mx.edu.utez.server.modules.status.model.Status;

import java.util.Set;

@Entity
@Table(name = "cancellation_reasons")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CancellationReason {
    public CancellationReason(String reason, Integer refundPercent, Status status) {
        this.reason = reason;
        this.refundPercent = refundPercent;
        this.status = status;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "VARCHAR(100)", nullable = false)
    private String reason;

    @Column(columnDefinition = "TINYINT UNSIGNED", nullable = false)
    private Integer refundPercent;

    // Relationships <-
    @ManyToOne
    @JoinColumn(name = "status_id", referencedColumnName = "id",
            nullable = false)
    private Status status;

    // Relationships ->
    @ManyToMany(mappedBy = "cancellationReasons")
    Set<Appointment> appointments;
}
