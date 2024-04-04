package mx.edu.utez.server.modules.doctor.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IDoctorRepository extends JpaRepository<Doctor, Long> {
    boolean existsBySpeciality_Id(Long id);

    @Query(value = "SELECT COUNT(*) " +
            "FROM doctors d " +
            "INNER JOIN people p ON d.person_id = p.id " +
            "WHERE LOWER(TRIM(CONCAT(p.name, ' ', p.surname, COALESCE(' ' || p.lastname, '')))) = LOWER(?1) " +
            "OR p.curp = ?2 " +
            "OR p.email = ?3", nativeQuery = true)
    Long existsByFullNameOrCurpOrEmail(String fullname, String curp, String email);
}
