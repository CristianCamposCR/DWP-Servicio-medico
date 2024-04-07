package mx.edu.utez.server.modules.security.service;

import lombok.RequiredArgsConstructor;
import mx.edu.utez.server.kernel.Errors;
import mx.edu.utez.server.modules.security.entities.UserDetailsImpl;
import mx.edu.utez.server.modules.user.model.IUserRepository;
import mx.edu.utez.server.modules.user.model.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final IUserRepository iUserRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            Optional<User> user = Optional.ofNullable(iUserRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(Errors.NO_USER_FOUND.name())));
            if (user.isPresent()) {
                return UserDetailsImpl.build(user.get());
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
