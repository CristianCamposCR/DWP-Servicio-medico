package mx.edu.utez.server.modules.area.model;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IAreaRepository extends JpaRepository<Area, Long> {
    Page<Area> findAllByNameContainingIgnoreCase(String name, Pageable pageable);

    public boolean existsByName(String name);

    public boolean existsByNameAndIdNot(String name, Long areaId);
}
