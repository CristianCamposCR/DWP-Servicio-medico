package mx.edu.utez.server.modules.doctor.model;

import mx.edu.utez.server.kernel.Statuses;
import mx.edu.utez.server.modules.doctor.controller.dto.IDoctorListView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface IDoctorRepository extends JpaRepository<Doctor, Long> {
    boolean existsBySpeciality_Id(Long id);

    @Query(value = "SELECT COUNT(*) " +
            "FROM doctors d " +
            "INNER JOIN people p ON d.person_id = p.id " +
            "WHERE LOWER(TRIM(CONCAT(p.name, ' ', p.surname, COALESCE(' ' || p.lastname, '')))) = LOWER(?1) " +
            "OR p.curp = ?2 " +
            "OR p.email = ?3", nativeQuery = true)
    Long existsByFullNameOrCurpOrEmail(String fullName, String curp, String email);

    @Query(value = "SELECT COUNT(*) " +
            "FROM doctors d " +
            "INNER JOIN people p ON d.person_id = p.id " +
            "WHERE LOWER(TRIM(CONCAT(p.name, ' ', p.surname, COALESCE(' ' || p.lastname, '')))) = LOWER(?1) " +
            "OR p.curp = ?2 " +
            "OR p.email = ?3 " +
            "OR d.professional_id = ?4", nativeQuery = true)
    Long existsByFullNameOrCurpOrEmailOrProfessionalId(String fullName, String curp, String email, String professionalId);

    @Query(value = """
            SELECT d.* FROM doctors d
            INNER JOIN people p ON d.person_id = p.id 
            INNER JOIN users u ON u.person_id = p.id 
            INNER JOIN statuses s ON u.status_id = s.id 
            WHERE LOWER(TRIM(CONCAT(p.name, ' ', p.surname, COALESCE(' ' || p.lastname, '')))) LIKE LOWER(CONCAT('%', ?1, '%'))
            """,
            nativeQuery = true,
            countQuery = "SELECT COUNT(*) FROM doctors")
    Page<Doctor> findAllBySearchValue(String searchValue, Pageable pageable);

    @Query(value = """
            SELECT d.* FROM doctors d
            INNER JOIN people p ON d.person_id = p.id 
            INNER JOIN users u ON u.person_id = p.id 
            INNER JOIN statuses s ON u.status_id = s.id 
            WHERE LOWER(TRIM(CONCAT(p.name, ' ', p.surname, COALESCE(' ' || p.lastname, '')))) LIKE LOWER(CONCAT('%', ?1, '%'))
            AND s.name <> ?2
            """,
            nativeQuery = true,
            countQuery = """
                    SELECT COUNT(*) FROM doctors d
                    INNER JOIN people p ON d.person_id = p.id 
                    INNER JOIN user u ON u.person_id = p.id
                    INNER JOIN status s ON s.id = u.status_id
                    WHERE s.name <> 'INACTIVO'
                    """)
    Page<Doctor> findAllBySearchValueAndStatusNameNot(String searchValue, Statuses statusName, Pageable pageable);

    Page<Doctor> findAllByPerson_User_Status_NameNot(Statuses statusName, Pageable pageable);

    Optional<Doctor> findByIdAndPerson_User_StatusNameNot(Long id, Statuses statusName);

    boolean existsByProfessionalIdAndIdNot(String professionalId, Long id);

    Optional<Doctor> findByPerson_User_UsernameAndPerson_User_Status_Name_Not(String username, Statuses statusName);

    @Query(value = """
            SELECT (COUNT(*) * 6)
            FROM doctors d
                     INNER JOIN shifts sh ON d.shift_id = sh.id
                     INNER JOIN people p ON d.person_id = p.id
                     INNER JOIN users u ON p.id = u.person_id
                     INNER JOIN statuses st ON st.id = u.status_id
            WHERE d.speciality_id = ?1
              AND d.shift_id = ?2
              AND NOT d.is_aux
              AND st.name = 'ACTIVO'
              AND d.available_days LIKE CONCAT('%', ?3, '%');
            """, nativeQuery = true)
    Long checkAvailability(Long specialityId, Long shiftId, String dayName);

    @Query(value = """
            SELECT d.id, TRIM(CONCAT(p.name, ' ', p.surname, COALESCE(CONCAT(' ', p.lastname), ''))) AS 'fullName'
            FROM doctors d
                     INNER JOIN people p on d.person_id = p.id
                     LEFT JOIN (SELECT a.doctor_id, COUNT(*) AS appointment_count
                                FROM appointments a
                                WHERE a.scheduled_at = ?1
                                GROUP BY a.doctor_id) AS appointment_counts ON d.id = appointment_counts.doctor_id
            WHERE d.speciality_id = ?2
              AND d.shift_id = ?3
              AND NOT d.is_aux
              AND (appointment_counts.appointment_count IS NULL OR appointment_counts.appointment_count < 6)
            """, nativeQuery = true)
    Set<IDoctorListView> findAllAvailableDoctors(LocalDate scheduledAt, Long specialityId, Long shiftId);

    @Query(value = """
            SELECT d.id, TRIM(CONCAT(p.name, ' ', p.surname, COALESCE(CONCAT(' ', p.lastname), ''))) AS 'fullName'
            FROM doctors d
                     INNER JOIN people p on d.person_id = p.id
                     LEFT JOIN (SELECT a.doctor_id, COUNT(*) AS appointment_count
                                FROM appointments a
                                WHERE a.scheduled_at = ?1
                                GROUP BY a.doctor_id) AS appointment_counts ON d.id = appointment_counts.doctor_id
            WHERE d.speciality_id = ?2
              AND d.shift_id = ?3
              AND d.is_aux
              AND (appointment_counts.appointment_count IS NULL OR appointment_counts.appointment_count < 6)
            """, nativeQuery = true)
    Set<IDoctorListView> findAllAvailableAuxDoctors(LocalDate scheduledAt, Long specialityId, Long shiftId);

    @Query(value = """
            SELECT scheduled_hour
            FROM appointments
            WHERE scheduled_at = ?1
              AND doctor_id = ?2
            """, nativeQuery = true)
    List<Integer> getBusyHours(LocalDate scheduledAt, Long doctorId);
}
