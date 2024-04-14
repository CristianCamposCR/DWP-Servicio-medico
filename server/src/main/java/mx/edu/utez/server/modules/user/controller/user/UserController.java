package mx.edu.utez.server.modules.user.controller.user;

import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import mx.edu.utez.server.kernel.Errors;
import mx.edu.utez.server.modules.user.controller.dto.ChangePasswordDto;
import mx.edu.utez.server.modules.user.controller.dto.RecoveryPasswordDto;
import mx.edu.utez.server.modules.user.service.UserService;
import mx.edu.utez.server.utils.Methods;
import mx.edu.utez.server.utils.ResponseApi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/user")
@CrossOrigin(origins = {"*"})
@RequiredArgsConstructor
public class UserController {
    Logger logger = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;

    @PreAuthorize("hasAnyAuthority('DOCTOR', 'PATIENT')")
    @PostMapping("/change-password/")
    public ResponseEntity<ResponseApi<Boolean>> changePassword(@RequestBody @Valid ChangePasswordDto dto) {
        try {
            String username = Methods.getLoggedUsername();
            ResponseApi<Boolean> responseApi = this.userService.credentialsChangePassword(dto, username);
            return new ResponseEntity<>(responseApi, responseApi.getStatus());
        } catch (MessagingException e) {
            logger.error(e.getMessage());
            return ResponseEntity.badRequest().body(new ResponseApi<>(HttpStatus.BAD_REQUEST, true, e.getMessage()));
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseEntity.internalServerError().body(
                    new ResponseApi<>(HttpStatus.INTERNAL_SERVER_ERROR, true, Errors.SERVER_ERROR.name())
            );
        }
    }

    @PostMapping("/recovery-password/")
    public ResponseEntity<ResponseApi<Boolean>> recoveryPassword(@RequestBody @Valid RecoveryPasswordDto dto) {
        try {
            String username = Methods.getLoggedUsername();
            ResponseApi<Boolean> responseApi = this.userService.credentialsRecoveryPassword(dto, username);
            return new ResponseEntity<>(responseApi, responseApi.getStatus());
        } catch (MessagingException e) {
            logger.error(e.getMessage());
            return ResponseEntity.badRequest().body(new ResponseApi<>(HttpStatus.BAD_REQUEST, true, e.getMessage()));
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseEntity.internalServerError().body(
                    new ResponseApi<>(HttpStatus.INTERNAL_SERVER_ERROR, true, Errors.SERVER_ERROR.name())
            );
        }
    }
}
