package mx.edu.utez.server.modules.appointment.model;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.time.LocalDate;

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

    Long countByScheduledAtAndSpeciality_Id(LocalDate date, Long specialityId);

    @Query(value = """
            SELECT a.* FROM appointments a
                     INNER JOIN patients pa on a.patient_id = pa.id
                     INNER JOIN people pe on pa.person_id = pe.id
                     INNER JOIN users u on pe.id = u.person_id
                     INNER JOIN statuses s on u.status_id = s.id
                     INNER JOIN statuses s2 on a.status_id = s2.id
            WHERE LOWER(a.folio) LIKE LOWER(CONCAT('%', ?1, '%'))
                AND u.username = ?2
                AND (s2.name = 'AGENDADA'
               OR s2.name = 'REAGENDADA')
                AND s.name = 'ACTIVO'
            """, nativeQuery = true,
            countQuery = """
                    SELECT COUNT(*) FROM appointments a
                                         INNER JOIN patients pa on a.patient_id = pa.id
                                         INNER JOIN people pe on pa.person_id = pe.id
                                         INNER JOIN users u on pe.id = u.person_id
                                         INNER JOIN statuses s on u.status_id = s.id
                                         INNER JOIN statuses s2 on a.status_id = s2.id
                    WHERE LOWER(a.folio) LIKE LOWER(CONCAT('%', ?1, '%'))
                        AND u.username = ?2
                        AND (s2.name = 'AGENDADA'
                        OR s2.name = 'REAGENDADA')
                        AND s.name = 'ACTIVO'
                    """)
    Page<Appointment> findAllPendingByPatientAndSearchValue(String searchValue, String username, Pageable pageable);

    @Query(value = """
            SELECT a.* FROM appointments a
                     INNER JOIN patients pa on a.patient_id = pa.id
                     INNER JOIN people pe on pa.person_id = pe.id
                     INNER JOIN users u on pe.id = u.person_id
                     INNER JOIN statuses s on u.status_id = s.id
                     INNER JOIN statuses s2 on a.status_id = s2.id
            WHERE u.username = ?1
                AND (s2.name = 'AGENDADA'
               OR s2.name = 'REAGENDADA')
                AND s.name = 'ACTIVO'
            """, nativeQuery = true,
            countQuery = """
                    SELECT COUNT(*) FROM appointments a
                                         INNER JOIN patients pa on a.patient_id = pa.id
                                         INNER JOIN people pe on pa.person_id = pe.id
                                         INNER JOIN users u on pe.id = u.person_id
                                         INNER JOIN statuses s on u.status_id = s.id
                                         INNER JOIN statuses s2 on a.status_id = s2.id
                    WHERE u.username = ?1
                        AND (s2.name = 'AGENDADA'
                        OR s2.name = 'REAGENDADA')
                        AND s.name = 'ACTIVO'
                    """)
    Page<Appointment> findAllPendingByPatient(String username, Pageable pageable);

    @Query(value = """
            SELECT a.* FROM appointments a
                     INNER JOIN patients pa on a.patient_id = pa.id
                     INNER JOIN people pe on pa.person_id = pe.id
                     INNER JOIN users u on pe.id = u.person_id
                     INNER JOIN statuses s on u.status_id = s.id
                     INNER JOIN statuses s2 on a.status_id = s2.id
            WHERE LOWER(a.folio) LIKE LOWER(CONCAT('%', ?1, '%'))
                AND u.username = ?2
                AND s2.name = 'CONFIRMADA'
                AND s.name = 'ACTIVO'
            """, nativeQuery = true,
            countQuery = """
                    SELECT COUNT(*) FROM appointments a
                                         INNER JOIN patients pa on a.patient_id = pa.id
                                         INNER JOIN people pe on pa.person_id = pe.id
                                         INNER JOIN users u on pe.id = u.person_id
                                         INNER JOIN statuses s on u.status_id = s.id
                                         INNER JOIN statuses s2 on a.status_id = s2.id
                    WHERE LOWER(a.folio) LIKE LOWER(CONCAT('%', ?1, '%'))
                        AND u.username = ?2
                        AND s2.name = 'CONFIRMADA'
                        AND s.name = 'ACTIVO'
                    """)
    Page<Appointment> findAllActiveByPatientAndSearchValue(String searchValue, String username, Pageable pageable);

    @Query(value = """
            SELECT a.* FROM appointments a
                     INNER JOIN patients pa on a.patient_id = pa.id
                     INNER JOIN people pe on pa.person_id = pe.id
                     INNER JOIN users u on pe.id = u.person_id
                     INNER JOIN statuses s on u.status_id = s.id
                     INNER JOIN statuses s2 on a.status_id = s2.id
            WHERE u.username = ?1
                AND s2.name = 'CONFIRMADA'
                AND s.name = 'ACTIVO'
            """, nativeQuery = true,
            countQuery = """
                    SELECT COUNT(*) FROM appointments a
                                         INNER JOIN patients pa on a.patient_id = pa.id
                                         INNER JOIN people pe on pa.person_id = pe.id
                                         INNER JOIN users u on pe.id = u.person_id
                                         INNER JOIN statuses s on u.status_id = s.id
                                         INNER JOIN statuses s2 on a.status_id = s2.id
                    WHERE u.username = ?1
                        AND s2.name = 'CONFIRMADA'
                        AND s.name = 'ACTIVO'
                    """)
    Page<Appointment> findAllActiveByPatient(String username, Pageable pageable);

    @Query(value = """
            SELECT a.* FROM appointments a
                     INNER JOIN patients pa on a.patient_id = pa.id
                     INNER JOIN people pe on pa.person_id = pe.id
                     INNER JOIN users u on pe.id = u.person_id
                     INNER JOIN statuses s on u.status_id = s.id
                     INNER JOIN statuses s2 on a.status_id = s2.id
            WHERE LOWER(a.folio) LIKE LOWER(CONCAT('%', ?1, '%'))
                AND u.username = ?2
                AND (s2.name = 'ATENDIDA'
               OR s2.name = 'CANCELADA'
               OR s2.name = 'EXPIRADA')
                AND s.name = 'ACTIVO'
            """, nativeQuery = true,
            countQuery = """
                    SELECT COUNT(*) FROM appointments a
                              INNER JOIN patients pa on a.patient_id = pa.id
                              INNER JOIN people pe on pa.person_id = pe.id
                              INNER JOIN users u on pe.id = u.person_id
                              INNER JOIN statuses s on u.status_id = s.id
                              INNER JOIN statuses s2 on a.status_id = s2.id
                    WHERE LOWER(a.folio) LIKE LOWER(CONCAT('%', ?1, '%'))
                        AND u.username = ?2
                        AND (s2.name = 'ATENDIDA'
                        OR s2.name = 'CANCELADA'
                        OR s2.name = 'EXPIRADA')
                        AND s.name = 'ACTIVO'
                    """)
    Page<Appointment> findAllHistoryByPatientAndSearchValue(String searchValue, String username, Pageable pageable);

    @Query(value = """
            SELECT a.* FROM appointments a
                     INNER JOIN patients pa on a.patient_id = pa.id
                     INNER JOIN people pe on pa.person_id = pe.id
                     INNER JOIN users u on pe.id = u.person_id
                     INNER JOIN statuses s on u.status_id = s.id
                     INNER JOIN statuses s2 on a.status_id = s2.id
            WHERE u.username = ?1
                AND (s2.name = 'ATENDIDA'
               OR s2.name = 'CANCELADA'
               OR s2.name = 'EXPIRADA')
                AND s.name = 'ACTIVO'
            """, nativeQuery = true,
            countQuery = """
                    SELECT COUNT(*) FROM appointments a
                            INNER JOIN patients pa on a.patient_id = pa.id
                            INNER JOIN people pe on pa.person_id = pe.id
                            INNER JOIN users u on pe.id = u.person_id
                            INNER JOIN statuses s on u.status_id = s.id
                            INNER JOIN statuses s2 on a.status_id = s2.id
                    WHERE u.username = ?1
                        AND (s2.name = 'ATENDIDA'
                        OR s2.name = 'CANCELADA'
                        OR s2.name = 'EXPIRADA')
                        AND s.name = 'ACTIVO'
                    """)
    Page<Appointment> findAllHistoryByPatient(String username, Pageable pageable);

    @Query(value = """
            SELECT a.* FROM appointments a
                     INNER JOIN doctors d on a.patient_id = d.id
                     INNER JOIN people p on d.person_id = p.id
                     INNER JOIN users u on p.id = u.person_id
                     INNER JOIN statuses s on u.status_id = s.id
                     INNER JOIN statuses s2 on a.status_id = s2.id
            WHERE LOWER(a.folio) LIKE LOWER(CONCAT('%', ?1, '%'))
              AND u.username = ?2
              AND s2.name = 'CONFIRMADA'
              AND s.name = 'ACTIVO'
            """, nativeQuery = true,
            countQuery = """
                    SELECT COUNT(*) FROM appointments a
                             INNER JOIN doctors d on a.patient_id = d.id
                             INNER JOIN people p on d.person_id = p.id
                             INNER JOIN users u on p.id = u.person_id
                             INNER JOIN statuses s on u.status_id = s.id
                             INNER JOIN statuses s2 on a.status_id = s2.id
                    WHERE LOWER(a.folio) LIKE LOWER(CONCAT('%', ?1, '%'))
                      AND u.username = ?2
                      AND s2.name = 'CONFIRMADA'
                      AND s.name = 'ACTIVO'
                            """)
    Page<Appointment> findAllAssignedByDoctorAndSearchValue(String searchValue, String username, Pageable pageable);

    @Query(value = """
            SELECT a.* FROM appointments a
                     INNER JOIN doctors d on a.patient_id = d.id
                     INNER JOIN people p on d.person_id = p.id
                     INNER JOIN users u on p.id = u.person_id
                     INNER JOIN statuses s on u.status_id = s.id
                     INNER JOIN statuses s2 on a.status_id = s2.id
            WHERE u.username = ?1
              AND s2.name = 'CONFIRMADA'
              AND s.name = 'ACTIVO'
            """, nativeQuery = true,
            countQuery = """
                    SELECT COUNT(*) FROM appointments a
                             INNER JOIN doctors d on a.patient_id = d.id
                             INNER JOIN people p on d.person_id = p.id
                             INNER JOIN users u on p.id = u.person_id
                             INNER JOIN statuses s on u.status_id = s.id
                             INNER JOIN statuses s2 on a.status_id = s2.id
                    WHERE u.username = ?1
                      AND s2.name = 'CONFIRMADA'
                      AND s.name = 'ACTIVO'
                            """)
    Page<Appointment> findAllAssignedByDoctor(String username, Pageable pageable);

    @Query(value = """
            SELECT a.* FROM appointments a
                     INNER JOIN doctors d on a.patient_id = d.id
                     INNER JOIN people p on d.person_id = p.id
                     INNER JOIN users u on p.id = u.person_id
                     INNER JOIN statuses s on u.status_id = s.id
                     INNER JOIN statuses s2 on a.status_id = s2.id
            WHERE LOWER(a.folio) LIKE LOWER(CONCAT('%', ?1, '%'))
              AND u.username = ?2
              AND (s2.name = 'ATENDIDA'
                OR s2.name = 'CANCELADA'
                OR s2.name = 'EXPIRADA')
              AND s.name = 'ACTIVO'
            """, nativeQuery = true,
            countQuery = """
                    SELECT COUNT(*) FROM appointments a
                             INNER JOIN doctors d on a.patient_id = d.id
                             INNER JOIN people p on d.person_id = p.id
                             INNER JOIN users u on p.id = u.person_id
                             INNER JOIN statuses s on u.status_id = s.id
                             INNER JOIN statuses s2 on a.status_id = s2.id
                    WHERE LOWER(a.folio) LIKE LOWER(CONCAT('%', ?1, '%'))
                      AND u.username = ?2
                      AND (s2.name = 'ATENDIDA'
                        OR s2.name = 'CANCELADA'
                        OR s2.name = 'EXPIRADA')
                      AND s.name = 'ACTIVO'
                            """)
    Page<Appointment> findAllHistoryByDoctorAndSearchValue(String searchValue, String username, Pageable pageable);

    @Query(value = """
            SELECT a.* FROM appointments a
                     INNER JOIN doctors d on a.patient_id = d.id
                     INNER JOIN people p on d.person_id = p.id
                     INNER JOIN users u on p.id = u.person_id
                     INNER JOIN statuses s on u.status_id = s.id
                     INNER JOIN statuses s2 on a.status_id = s2.id
            WHERE u.username = ?1
              AND (s2.name = 'ATENDIDA'
                OR s2.name = 'CANCELADA'
                OR s2.name = 'EXPIRADA')
              AND s.name = 'ACTIVO'
            """, nativeQuery = true,
            countQuery = """
                    SELECT COUNT(*) FROM appointments a
                             INNER JOIN doctors d on a.patient_id = d.id
                             INNER JOIN people p on d.person_id = p.id
                             INNER JOIN users u on p.id = u.person_id
                             INNER JOIN statuses s on u.status_id = s.id
                             INNER JOIN statuses s2 on a.status_id = s2.id
                    WHERE u.username = ?1   
                      AND (s2.name = 'ATENDIDA'
                        OR s2.name = 'CANCELADA'
                        OR s2.name = 'EXPIRADA')
                      AND s.name = 'ACTIVO'
                            """)
    Page<Appointment> findAllHistoryByDoctor(String username, Pageable pageable);

    @Query(value = """
            SELECT a.* FROM appointments a
                     INNER JOIN statuses s on a.status_id = s.id
            WHERE LOWER(a.folio) LIKE LOWER(CONCAT('%', ?1, '%'))
              AND (s.name = 'AGENDADA'
                OR s.name = 'REAGENDADA')
            """, nativeQuery = true,
            countQuery = """
                    SELECT COUNT(*) FROM appointments a
                            INNER JOIN statuses s on a.status_id = s.id
                    WHERE LOWER(a.folio) LIKE LOWER(CONCAT('%', ?1, '%'))
                        AND (s.name = 'AGENDADA'
                        OR s.name = 'REAGENDADA')
                    """)
    Page<Appointment> findAllPendingBySearchValue(String searchValue, Pageable pageable);

    @Query(value = """
            SELECT a.* FROM appointments a
                     INNER JOIN statuses s on a.status_id = s.id
            WHERE s.name = 'AGENDADA' OR s.name = 'REAGENDADA'
            """, nativeQuery = true,
            countQuery = """
                    SELECT COUNT(*) FROM appointments a
                             INNER JOIN statuses s on a.status_id = s.id
                    WHERE s.name = 'AGENDADA' OR s.name = 'REAGENDADA'
                    """)
    Page<Appointment> findAllPending(Pageable pageable);

    @Query(value = """
            SELECT a.* FROM appointments a
                     INNER JOIN statuses s on a.status_id = s.id
            WHERE LOWER(a.folio) LIKE LOWER(CONCAT('%', ?1, '%'))
              AND s.name = 'CONFIRMADA'
            """, nativeQuery = true,
            countQuery = """
                    SELECT COUNT(*) FROM appointments a
                            INNER JOIN statuses s on a.status_id = s.id
                    WHERE LOWER(a.folio) LIKE LOWER(CONCAT('%', ?1, '%'))
                      AND s.name = 'CONFIRMADA'
                      """)
    Page<Appointment> findAllActiveBySearchValue(String searchValue, Pageable pageable);

    @Query(value = """
            SELECT a.* FROM appointments a
                     INNER JOIN statuses s on a.status_id = s.id
            WHERE s.name = 'CONFIRMADA'
            """, nativeQuery = true,
            countQuery = """
                    SELECT COUNT(*) FROM appointments a
                            INNER JOIN statuses s on a.status_id = s.id
                    WHERE s.name = 'CONFIRMADA'
                    """)
    Page<Appointment> findAllActive(Pageable pageable);

    @Query(value = """
            SELECT a.* FROM appointments a
                     INNER JOIN statuses s on a.status_id = s.id
            WHERE LOWER(a.folio) LIKE LOWER(CONCAT('%', ?1, '%'))
              AND (s.name = 'ATENDIDA'
                OR s.name = 'CANCELADA'
                OR s.name = 'EXPIRADA')
            """, nativeQuery = true,
            countQuery = """
                    SELECT COUNT(*) FROM appointments a
                             INNER JOIN statuses s on a.status_id = s.id
                    WHERE LOWER(a.folio) LIKE LOWER(CONCAT('%', ?1, '%'))
                      AND (s.name = 'ATENDIDA'
                        OR s.name = 'CANCELADA'
                        OR s.name = 'EXPIRADA')
                    """)
    Page<Appointment> findAllHistoryBySearchValue(String searchValue, Pageable pageable);

    @Query(value = """
            SELECT a.* FROM appointments a
                     INNER JOIN statuses s on a.status_id = s.id
            WHERE s.name = 'ATENDIDA'
                OR s.name = 'CANCELADA'
                OR s.name = 'EXPIRADA'
            """, nativeQuery = true,
            countQuery = """
                    SELECT COUNT(*) FROM appointments a
                             INNER JOIN statuses s on a.status_id = s.id
                    WHERE s.name = 'ATENDIDA'
                        OR s.name = 'CANCELADA'
                        OR s.name = 'EXPIRADA'
                    """)
    Page<Appointment> findAllHistory(Pageable pageable);
}
