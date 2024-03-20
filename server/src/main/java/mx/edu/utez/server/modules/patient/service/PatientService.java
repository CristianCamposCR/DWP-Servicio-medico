package mx.edu.utez.server.modules.patient.service;

import mx.edu.utez.server.modules.patient.model.IPatientRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PatientService {
    private final IPatientRepository iPatientRepository;

    public PatientService(IPatientRepository iPatientRepository) {
        this.iPatientRepository = iPatientRepository;
    }
}