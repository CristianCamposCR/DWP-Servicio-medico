package mx.edu.utez.server.modules.dbBinnacle.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "VARCHAR(45)", nullable = false)
    private String action;

    @Column(columnDefinition = "VARCHAR(100)", nullable = false)
    private String table_name;

    @Column(columnDefinition = "JSON")
    private String from_record;

    @Column(columnDefinition = "JSON")
    private String to_record;

    @Column(nullable = false, insertable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Instant createdAt;

    @Column(columnDefinition = "BIGINT")
    private Long clientUserId;

    @Column(columnDefinition = "VARCHAR(100)")
    private String dbUser;
}
