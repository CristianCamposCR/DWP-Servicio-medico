package mx.edu.utez.server.modules.dbBinnacle.service;

import mx.edu.utez.server.modules.dbBinnacle.model.IDbBinnacleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class DbBinnacleService {
    private final IDbBinnacleRepository iDbBinnacleRepository;

    public DbBinnacleService(IDbBinnacleRepository iDbBinnacleRepository) {
        this.iDbBinnacleRepository = iDbBinnacleRepository;
    }
}