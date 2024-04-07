package mx.edu.utez.server.modules.person.model;

import mx.edu.utez.server.kernel.Statuses;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IPersonRepository extends JpaRepository<Person, Long> {
    @Query(value = "SELECT * " +
            "FROM people " +
            "WHERE LOWER(TRIM(CONCAT(name, ' ', surname, COALESCE(' ' || lastname, '')))) = LOWER(?1) " +
            "OR curp = ?2 " +
            "OR email = ?3", nativeQuery = true)
    Optional<Person> findByFullnameOrCurpOrEmail(String fullname, String curp, String email);

    Optional<Person> findByUser_UsernameIgnoreCase(String email);

    @Query(value = "SELECT * " +
            "FROM people " +
            "WHERE (LOWER(TRIM(CONCAT(name, ' ', surname, COALESCE(' ' || lastname, '')))) = LOWER(?1) " +
            "OR curp = ?2 " +
            "OR email = ?3) " +
            "AND id <> ?4", nativeQuery = true)
    Optional<Person> findByFullnameOrCurpOrEmailAndIdNot(String fullname, String curp, String email, Long id);

    Optional<Person> findByIdAndUser_Status_NameNot(Long id, Statuses statusName);
    boolean existsByIdAndUser_Status_NameNot(Long id, Statuses statusName);
}
