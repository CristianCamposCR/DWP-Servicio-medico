package mx.edu.utez.server.modules.db_binnacle.service;

import lombok.RequiredArgsConstructor;
import mx.edu.utez.server.modules.db_binnacle.model.DbBinnacle;
import mx.edu.utez.server.modules.db_binnacle.model.IDbBinnacleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class DbBinnacleService {
    Logger logger = LoggerFactory.getLogger(DbBinnacleService.class);
    private final IDbBinnacleRepository iDbBinnacleRepository;
    @Value("${spring.datasource.username}")
    private String dbUsername;


    public void log(String action, String tableName, String serviceName, String methodName, String username) {
        logger.info("Username: {}", username);
        logger.info("Action: {}", action);
        logger.info("Tabla: {}", tableName);
        logger.info("Servicio: {}", serviceName);
        logger.info("Método: {}", methodName);
        this.iDbBinnacleRepository.saveAndFlush(new DbBinnacle(
                action,
                serviceName,
                tableName,
                username,
                dbUsername
        ));
    }
}