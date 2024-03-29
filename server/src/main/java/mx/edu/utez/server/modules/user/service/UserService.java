package mx.edu.utez.server.modules.user.service;

import lombok.RequiredArgsConstructor;
import mx.edu.utez.server.modules.user.model.IUserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {
    private final IUserRepository iUserRepository;
}