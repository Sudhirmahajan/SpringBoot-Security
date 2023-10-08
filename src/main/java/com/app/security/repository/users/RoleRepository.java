package com.app.security.repository.users;

import com.app.security.domain.user.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);

    @Query(nativeQuery = true,
            value = """
                    select r.roleid, r.name from Role_tbl r inner join group_role_mapping grm
                   on r.roleid = grm.role_id inner join group_tbl g on  grm.group_id = g.id
                    where g.name in (:name)
                    """
    )
    List<Role> findByGroupNames(List<String> name);

    @Override
    void delete(Role role);

    List<Role> findByRoleIdIn(List<Long> roleIds);
}
