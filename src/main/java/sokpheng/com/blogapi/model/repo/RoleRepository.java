package sokpheng.com.blogapi.model.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sokpheng.com.blogapi.model.entities.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findRoleByName(String roleName);
}
