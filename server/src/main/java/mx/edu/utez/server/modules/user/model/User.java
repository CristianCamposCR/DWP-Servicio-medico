package mx.edu.utez.server.modules.user.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.edu.utez.server.modules.person.model.Person;
import mx.edu.utez.server.modules.role.model.Role;
import mx.edu.utez.server.modules.status.model.Status;
import mx.edu.utez.server.modules.verificationCode.model.VerificationCode;

import java.time.Instant;
import java.util.Set;

@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "VARCHAR(100)", nullable = false)
    private String username;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String password;

    @Column(columnDefinition = "DATETIME", nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Instant createdAt;

    @Column(columnDefinition = "DATETIME", insertable = false)
    private Instant updatedAt;

    // Relationships <-
    @ManyToOne
    @JoinColumn(name = "status_id", referencedColumnName = "id",
            nullable = false)
    private Status status;

    @ManyToOne
    @JoinColumn(name = "role_id", referencedColumnName = "id",
            nullable = false)
    private Role role;

    @OneToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @JoinColumn(name = "person_id", referencedColumnName = "id",
            nullable = false)
    @JsonIgnore
    private Person person;

    // Relationships ->
    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private Set<VerificationCode> verificationCodes;

    // Methods
    @PrePersist
    public void setCreatedAt() {
        this.createdAt = Instant.now();
    }

    @PreUpdate
    public void setUpdatedAt() {
        this.updatedAt = Instant.now();
    }
}
