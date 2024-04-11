package mx.edu.utez.server.modules.speciality.model;

import mx.edu.utez.server.kernel.Statuses;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface ISpecialityRepository extends JpaRepository<Speciality, Long> {
    Page<Speciality> findAllByNameContainingIgnoreCaseAndArea_Id(String name, Long areaId, Pageable pageable);

    Page<Speciality> findAllByNameContainingIgnoreCase(String name, Pageable pageable);

    Page<Speciality> findAllByArea_Id(Long areaId, Pageable pageable);

    Page<Speciality> findAllByNameContainingIgnoreCaseAndStatus_NameAndArea_Id(String name, Statuses statusName, Long areaId, Pageable pageable);

    Page<Speciality> findAllByNameContainingIgnoreCaseAndStatus_Name(String name, Statuses statusName, Pageable pageable);

    Page<Speciality> findAllByStatus_NameAndArea_Id(Statuses statusName, Long areaId, Pageable pageable);

    Page<Speciality> findAllByStatus_Name(Statuses statusName, Pageable pageable);

    Optional<Speciality> findByIdAndStatus_Name(Long id, Statuses statusName);

    Set<Speciality> findAllByStatus_Name(Statuses statusName);

    boolean existsByNameIgnoreCase(String name);

    boolean existsByNameIgnoreCaseAndIdNot(String name, Long id);

    boolean existsByArea_Id(Long areaId);

    boolean existsByIdAndStatus_Name(Long id, Statuses statusName);
}