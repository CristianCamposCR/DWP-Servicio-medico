package mx.edu.utez.server.modules.area.model;

import mx.edu.utez.server.kernel.Statuses;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface IAreaRepository extends JpaRepository<Area, Long> {
    Page<Area> findAllByNameContainingIgnoreCase(String name, Pageable pageable);

    Page<Area> findAllByStatus_Name(Statuses statusName, Pageable pageable);

    Page<Area> findAllByNameContainingIgnoreCaseAndStatus_Name(String name, Statuses statusName, Pageable pageable);

    Optional<Area> findByIdAndStatus_Name(Long id, Statuses statusName);

    Set<Area> findAllByStatus_Name(Statuses statusName);

    boolean existsByNameIgnoreCase(String name);

    boolean existsByNameIgnoreCaseAndIdNot(String name, Long areaId);

    boolean existsByIdAndStatus_Name(Long id, Statuses statusName);
}
