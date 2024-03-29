package mx.edu.utez.server.modules.role.service;

import lombok.RequiredArgsConstructor;
import mx.edu.utez.server.kernel.Roles;
import mx.edu.utez.server.modules.role.model.IRoleRepository;
import mx.edu.utez.server.modules.role.model.Role;
import mx.edu.utez.server.modules.status.model.Status;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class RoleService {
    private final IRoleRepository iRoleRepository;

    @Transactional(rollbackFor = {SQLException.class, Exception.class})
    public Role findOrSave(Roles name, Status status) {
        Optional<Role> optionalRole = this.iRoleRepository.findByName(name);
        return optionalRole.orElseGet(() -> this.iRoleRepository.saveAndFlush(new Role(name, status)));
    }
}
