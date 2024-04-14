package mx.edu.utez.server.modules.db_binnacle.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Entity
@Table(name = "db_binnacle")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DbBinnacle {
    public DbBinnacle(String action, String serviceName, String tableName, String username, String dbUser) {
        this.action = action;
        this.serviceName = serviceName;
        this.tableName = tableName;
        this.username = username;
        this.dbUser = dbUser;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "VARCHAR(45)", nullable = false)
    private String action;

    @Column(columnDefinition = "VARCHAR(100)", nullable = false)
    private String serviceName;

    @Column(columnDefinition = "VARCHAR(100)", nullable = false)
    private String tableName;

    @Column(columnDefinition = "DATETIME", nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Instant createdAt;

    @Column(columnDefinition = "VARCHAR(100)")
    private String username;

    @Column(columnDefinition = "VARCHAR(100)")
    private String dbUser;

    // Methods
    @PrePersist
    public void setCreatedAt() {
        this.createdAt = Instant.now();
    }
}
