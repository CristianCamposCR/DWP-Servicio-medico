package mx.edu.utez.server.modules.verification_code.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
import mx.edu.utez.server.kernel.MessageType;
import mx.edu.utez.server.modules.user.model.User;

import java.time.Instant;

@Entity
@Table(name = "verification_codes")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class VerificationCode {
    public VerificationCode(String code, MessageType messageType, Boolean wasUsed, Boolean isExpired, Boolean isInvalid, User user) {
        this.code = code;
        this.messageType = messageType;
        this.wasUsed = wasUsed;
        this.isExpired = isExpired;
        this.isInvalid = isInvalid;
        this.user = user;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT", nullable = false, updatable = false)
    private String code;

    @Column(columnDefinition = "VARCHAR(5)", nullable = false)
    @Enumerated(EnumType.STRING)
    private MessageType messageType;

    @Column(columnDefinition = "TINYINT DEFAULT 0", nullable = false)
    private Boolean wasUsed;

    @Column(columnDefinition = "TINYINT DEFAULT 0", nullable = false)
    private Boolean isExpired;

    @Column(columnDefinition = "TINYINT DEFAULT 0", nullable = false)
    private Boolean isInvalid;

    @Column(columnDefinition = "DATETIME", nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Instant createdAt;

    @Column(columnDefinition = "DATETIME", nullable = false, updatable = false)
    private Instant expireAt;

    // Relationships <-
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id",
            nullable = false)
    private User user;

    // Methods
    @PrePersist
    public void setAutoInsertFields() {
        this.createdAt = Instant.now();
        // La fecha de expiración es de 5 minutos
        this.expireAt = this.createdAt.plusSeconds(5 * 60L);
    }
}
