package mx.edu.utez.server.modules.verificationCode.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.edu.utez.server.modules.user.model.User;

import java.time.Instant;

@Entity
@Table(name = "verification_codes")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class VerificationCode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "VARCHAR(10)", nullable = false)
    private String code;

    @Column(columnDefinition = "VARCHAR(5)", nullable = false)
    private String messageType;

    @Column(columnDefinition = "TINYINT", nullable = false)
    private Boolean wasUsed;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Instant createdAt;

    @Column(nullable = false)
    private Instant expireAt;

    // Relationships <-
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id",
            nullable = false)
    private User user;
}
