package mx.edu.utez.server.modules.db_binnacle.service;

import lombok.RequiredArgsConstructor;
import mx.edu.utez.server.modules.db_binnacle.model.IDbBinnacleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class DbBinnacleService {
    private final IDbBinnacleRepository iDbBinnacleRepository;
}