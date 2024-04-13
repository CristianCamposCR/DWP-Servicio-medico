package mx.edu.utez.server.modules.auth.service;

import lombok.RequiredArgsConstructor;
import mx.edu.utez.server.kernel.Errors;
import mx.edu.utez.server.kernel.Statuses;
import mx.edu.utez.server.modules.auth.controller.dto.SignedDto;
import mx.edu.utez.server.modules.auth.controller.dto.UserSignInDto;
import mx.edu.utez.server.modules.security.jwt.JwtProvider;
import mx.edu.utez.server.modules.user.model.User;
import mx.edu.utez.server.modules.user.service.UserService;
import mx.edu.utez.server.utils.HashService;
import mx.edu.utez.server.utils.ResponseApi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthService {
    Logger logger = LoggerFactory.getLogger(AuthService.class);
    private final HashService hashService;
    private final UserService userService;
    private final JwtProvider jwtProvider;
    private final AuthenticationManager authenticationManager;

    public ResponseApi<SignedDto> signIn(UserSignInDto userSignInDto) {
        try {
            String password = hashService.decrypt(userSignInDto.getPassword());
            if (password == null)
                return new ResponseApi<>(HttpStatus.BAD_REQUEST, true, Errors.INVALID_FIELDS.name());

            Optional<User> optionalUser = this.userService.loadUserByUsername(userSignInDto.getUsername());
            if (optionalUser.isEmpty())
                return new ResponseApi<>(null, HttpStatus.NOT_FOUND, true, Errors.CREDENTIALS_MISMATCH.name());
            User user = optionalUser.get();
            if (user.getStatus().getName() == Statuses.NO_VERIFICADO)
                return new ResponseApi<>(null, HttpStatus.OK, true, Errors.USER_IS_NOT_VERIFIED.name());
            if (user.isBlocked())
                return new ResponseApi<>(null, HttpStatus.UNAUTHORIZED, true, Errors.USER_IS_BLOCKED.name());

            Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userSignInDto.getUsername(), password));
            SecurityContextHolder.getContext().setAuthentication(auth);
            String token = jwtProvider.generateToken(auth, user);
            SignedDto signedDto = new SignedDto(token);
            return new ResponseApi<>(signedDto, HttpStatus.OK, false, "ok");
        } catch (Exception e) {
            logger.error(e.getMessage());
            String message = Errors.CREDENTIALS_MISMATCH.name();
            if (e instanceof DisabledException)
                message = Errors.USER_IS_INACTIVE.name();
            return new ResponseApi<>(null, HttpStatus.UNAUTHORIZED, true, message);
        }
    }
}
