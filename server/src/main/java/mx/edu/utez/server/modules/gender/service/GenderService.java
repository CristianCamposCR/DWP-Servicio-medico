package mx.edu.utez.server.modules.gender.service;

import mx.edu.utez.server.modules.gender.model.IGenderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class GenderService {
    private final IGenderRepository iGenderRepository;

    public GenderService(IGenderRepository iGenderRepository) {
        this.iGenderRepository = iGenderRepository;
    }
}