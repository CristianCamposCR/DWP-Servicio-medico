package mx.edu.utez.server.modules.speciality.service;

import mx.edu.utez.server.modules.speciality.model.ISpecialityRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class SpecialityService {
    private final ISpecialityRepository iSpecialityRepository;

    public SpecialityService(ISpecialityRepository iSpecialityRepository) {
        this.iSpecialityRepository = iSpecialityRepository;
    }
}