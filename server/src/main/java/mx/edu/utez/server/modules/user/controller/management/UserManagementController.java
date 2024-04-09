package mx.edu.utez.server.modules.user.controller.management;

import lombok.RequiredArgsConstructor;
import mx.edu.utez.server.kernel.Errors;
import mx.edu.utez.server.modules.user.model.User;
import mx.edu.utez.server.modules.user.service.UserService;
import mx.edu.utez.server.utils.HashService;
import mx.edu.utez.server.utils.ResponseApi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/management/user")
@CrossOrigin(origins = {"*"})
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('ADMIN')")
public class UserManagementController {
    Logger logger = LoggerFactory.getLogger(UserManagementController.class);
    private final UserService userService;
    private final HashService hashService;

    @PatchMapping("/{id}")
    public ResponseEntity<ResponseApi<User>> changeStatus(@PathVariable("id") String encryptedId) {
        try {
            Long id = hashService.decryptId(encryptedId);
            ResponseApi<User> responseApi = this.userService.changeStatus(id);
            return new ResponseEntity<>(responseApi, responseApi.getStatus());
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseEntity.internalServerError().body(
                    new ResponseApi<>(HttpStatus.INTERNAL_SERVER_ERROR, true, Errors.SERVER_ERROR.name())
            );
        }
    }
}