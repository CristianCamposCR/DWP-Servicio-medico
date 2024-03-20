package mx.edu.utez.server.modules.doctor.service;

import mx.edu.utez.server.modules.doctor.model.IDoctorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class DoctorService {
    private final IDoctorRepository iDoctorRepository;

    public DoctorService(IDoctorRepository iDoctorRepository) {
        this.iDoctorRepository = iDoctorRepository;
    }
}