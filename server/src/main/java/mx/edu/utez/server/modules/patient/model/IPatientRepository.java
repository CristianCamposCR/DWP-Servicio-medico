package mx.edu.utez.server.modules.patient.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IPatientRepository extends JpaRepository<Patient, Long> {
    @Query(value = "SELECT COUNT(*) " +
            "FROM patients pa " +
            "INNER JOIN people p ON pa.person_id = p.id " +
            "WHERE LOWER(TRIM(CONCAT(p.name, ' ', p.surname, COALESCE(' ' || p.lastname, '')))) = LOWER(?1) " +
            "OR p.curp = ?2 " +
            "OR p.email = ?3", nativeQuery = true)
    Long existsByFullNameOrCurpOrEmail(String fullname, String curp, String email);

    @Query(value = "SELECT COUNT(*)" +
            " FROM patients pa" +
            " INNER JOIN people p on pa.person_id = p.id" +
            " WHERE (LOWER(TRIM(CONCAT(p.name, ' ', p.surname, COALESCE(' ' || p.lastname, '')))) = LOWER(?1)" +
            "   OR p.curp = ?2" +
            "   OR p.email = ?3) AND pa.id <> ?4", nativeQuery = true)
    Long existsByFullNameOrCurpOrEmailAndIdNot(String fullname, String curp, String email, Long id);
}
