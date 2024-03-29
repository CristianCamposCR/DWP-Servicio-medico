package mx.edu.utez.server.modules.patient.service;

import lombok.RequiredArgsConstructor;
import mx.edu.utez.server.modules.patient.model.IPatientRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class PatientService {
    private final IPatientRepository iPatientRepository;
}