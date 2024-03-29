package mx.edu.utez.server.modules.doctor.service;

import lombok.RequiredArgsConstructor;
import mx.edu.utez.server.modules.doctor.model.IDoctorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class DoctorService {
    private final IDoctorRepository iDoctorRepository;
}