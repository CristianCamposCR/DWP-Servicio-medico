package mx.edu.utez.server.modules.db_binnacle.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IDbBinnacleRepository extends JpaRepository<DbBinnacle, Long> {
}
