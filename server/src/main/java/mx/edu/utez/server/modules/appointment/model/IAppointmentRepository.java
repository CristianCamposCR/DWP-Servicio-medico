package mx.edu.utez.server.modules.appointment.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.Instant;

@Repository
public interface IAppointmentRepository extends JpaRepository<Appointment, Long> {
    boolean existsBySpeciality_Id(Long id);

    @Query(value = """
            SELECT COUNT(*)
            FROM appointments
            WHERE doctor_id = ?1
              AND scheduled_at >= ?2
            """, nativeQuery = true)
    Long existByDoctorId(Long id, Instant currentTime);

    @Query(value = """
            SELECT COUNT(*) FROM appointments a
                     INNER JOIN patients p on a.patient_id = p.id
                     INNER JOIN people p2 on p.person_id = p2.id
                     INNER JOIN users u on p2.id = u.person_id
            WHERE a.scheduled_at >= ?2 
            AND u.id = ?1
            """, nativeQuery = true)
    Long existsByPatientUserId(Long id, Instant currentTime);

    @Query(value = """
            SELECT COUNT(*) FROM appointments a
                     INNER JOIN doctors d on a.doctor_id = d.id
                     INNER JOIN people p on d.person_id = p.id
                     INNER JOIN users u on p.id = u.person_id
            WHERE a.scheduled_at >= ?2 
            AND u.id = ?1
            """, nativeQuery = true)
    Long existsByDoctorUserId(Long id, Instant currentTime);
}
