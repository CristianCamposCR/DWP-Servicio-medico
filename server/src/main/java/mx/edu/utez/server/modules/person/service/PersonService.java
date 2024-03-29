package mx.edu.utez.server.modules.person.service;

import lombok.RequiredArgsConstructor;
import mx.edu.utez.server.modules.person.model.IPersonRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class PersonService {
    private final IPersonRepository iPersonRepository;
}
