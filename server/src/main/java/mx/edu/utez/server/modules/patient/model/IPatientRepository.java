package mx.edu.utez.server.modules.patient.model;

import mx.edu.utez.server.kernel.Statuses;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IPatientRepository extends JpaRepository<Patient, Long> {
    @Query(value = "SELECT COUNT(*) " +
            "FROM patients pa " +
            "INNER JOIN people p ON pa.person_id = p.id " +
            "WHERE LOWER(TRIM(CONCAT(p.name, ' ', p.surname, COALESCE(' ' || p.lastname, '')))) = LOWER(?1) " +
            "OR p.curp = ?2 " +
            "OR p.email = ?3", nativeQuery = true)
    Long existsByFullNameOrCurpOrEmail(String fullname, String curp, String email);

    @Query(value = """
            SELECT pa.* FROM patients pa
            INNER JOIN people p ON pa.person_id = p.id 
            INNER JOIN users u ON u.person_id = p.id 
            INNER JOIN statuses s ON u.status_id = s.id 
            WHERE (LOWER(TRIM(CONCAT(p.name, ' ', p.surname, COALESCE(' ' OR p.lastname, '')))) LIKE LOWER(CONCAT('%', ?1, '%')) OR 
            LOWER(pa.patient_code) LIKE LOWER(CONCAT('%', ?1, '%')))
            AND s.name <> ?2
            """,
            nativeQuery = true,
            countQuery = """
                    SELECT COUNT(*) FROM patients pa
                    INNER JOIN people p ON pa.person_id = p.id 
                    INNER JOIN user u ON u.person_id = p.id
                    INNER JOIN status s ON s.id = u.status_id
                    WHERE s.name <> 'NO_VERIFICADO'
                    """)
    Page<Patient> findAllBySearchValueAndStatusNameNot(String searchValue, Statuses statusName, Pageable pageable);

    Page<Patient> findAllByPerson_User_Status_NameNot(Statuses statusName, Pageable pageable);

    Optional<Patient> findByIdAndPerson_User_StatusNameNot(Long id, Statuses statusName);

    Optional<Patient> findByPerson_User_UsernameAndPerson_User_Status_Name_Not(String username, Statuses statusName);

    @Query(value = "SELECT COUNT(*)" +
            " FROM patients pa" +
            " INNER JOIN people p on pa.person_id = p.id" +
            " WHERE (LOWER(TRIM(CONCAT(p.name, ' ', p.surname, COALESCE(' ' || p.lastname, '')))) = LOWER(?1)" +
            "   OR p.curp = ?2" +
            "   OR p.email = ?3) AND pa.id <> ?4", nativeQuery = true)
    Long existsByFullNameOrCurpOrEmailAndIdNot(String fullname, String curp, String email, Long id);

    boolean existsByIdAndPerson_User_Status_Name(Long id, Statuses statusName);
}
