package mx.edu.utez.server.modules.user.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.edu.utez.server.modules.person.model.Person;
import mx.edu.utez.server.modules.role.model.Role;
import mx.edu.utez.server.modules.status.model.Status;
import mx.edu.utez.server.modules.verificationCode.model.VerificationCode;

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

    // Relationships <-
    @ManyToOne
    @JoinColumn(name = "status_id", referencedColumnName = "id",
            nullable = false)
    private Status status;

    @ManyToOne
    @JoinColumn(name = "role_id", referencedColumnName = "id",
            nullable = false)
    private Role role;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "person_id", referencedColumnName = "id",
            nullable = false)
    private Person person;

    // Relationships ->
    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private Set<VerificationCode> verificationCodes;
}
