package mx.edu.utez.server.modules.cancellation_reason.module;

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
import mx.edu.utez.server.kernel.CancellationReasons;
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
    public CancellationReason(CancellationReasons reason, Integer refundPercent, Status status) {
        this.reason = reason;
        this.refundPercent = refundPercent;
        this.status = status;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "VARCHAR(100)", nullable = false)
    @Enumerated(EnumType.STRING)
    private CancellationReasons reason;

    @Column(columnDefinition = "TINYINT UNSIGNED", nullable = false)
    private Integer refundPercent;

    // Relationships <-
    @ManyToOne
    @JoinColumn(name = "status_id", referencedColumnName = "id",
            nullable = false)
    private Status status;

    // Relationships ->
    @OneToMany(mappedBy = "cancellationReason")
    @JsonIgnore
    Set<Appointment> appointments;
}
