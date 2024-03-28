package mx.edu.utez.server.modules.area.model;

import mx.edu.utez.server.kernel.Statuses;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface IAreaRepository extends JpaRepository<Area, Long> {
    Page<Area> findAllByNameContainingIgnoreCase(String name, Pageable pageable);

    Set<Area> findAllByStatus_Name(Statuses statusName);

    public boolean existsByName(String name);

    public boolean existsByNameAndIdNot(String name, Long areaId);
}
