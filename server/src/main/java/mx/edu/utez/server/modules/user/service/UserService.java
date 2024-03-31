package mx.edu.utez.server.modules.user.service;

import lombok.RequiredArgsConstructor;
import mx.edu.utez.server.modules.user.model.IUserRepository;
import mx.edu.utez.server.modules.user.model.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {
    private final IUserRepository iUserRepository;

    @Transactional(readOnly = true)
    public Optional<User> loadUserByUsername(String username) {
        return iUserRepository.findByUsername(username);
    }
}