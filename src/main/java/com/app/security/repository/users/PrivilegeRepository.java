package com.app.security.repository.users;

import com.app.security.domain.user.Privilege;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrivilegeRepository extends JpaRepository<Privilege, Long> {
    Privilege findByPermission(String name);

    @Override
    void delete(Privilege privilege);
}
