package mx.edu.utez.server.modules.role.model;

import mx.edu.utez.server.kernel.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IRoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(Roles name);
}
