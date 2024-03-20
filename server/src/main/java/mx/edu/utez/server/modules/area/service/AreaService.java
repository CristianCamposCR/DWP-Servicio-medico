package mx.edu.utez.server.modules.area.service;

import mx.edu.utez.server.modules.area.model.IAreaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AreaService {
    private final IAreaRepository iAreaRepository;

    public AreaService(IAreaRepository iAreaRepository) {
        this.iAreaRepository = iAreaRepository;
    }
}