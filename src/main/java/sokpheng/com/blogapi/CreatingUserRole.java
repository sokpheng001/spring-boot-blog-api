package sokpheng.com.blogapi;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import sokpheng.com.blogapi.model.entities.Role;
import sokpheng.com.blogapi.model.repo.RoleRepository;

import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class CreatingUserRole {

    private final RoleRepository roleRepository;

    @PostConstruct
    public void init() {
        log.info("Checking creating role for user");

        if (roleRepository.findRoleByName("USER") == null) {
            Role role = new Role();
            role.setName("USER");
            role.setUuid(UUID.randomUUID().toString());
            roleRepository.save(role);
            log.info("USER role created");
        }

        if (roleRepository.findRoleByName("ADMIN") == null) {
            Role role = new Role();
            role.setName("ADMIN");
            role.setUuid(UUID.randomUUID().toString());
            roleRepository.save(role);
            log.info("ADMIN role created");
        }
    }
}