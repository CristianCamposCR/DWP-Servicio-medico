package mx.edu.utez.server.modules.record.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IRecordRepository extends JpaRepository<Record, Long> {
}
