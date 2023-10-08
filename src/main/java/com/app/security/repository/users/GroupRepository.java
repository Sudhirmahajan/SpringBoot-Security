package com.app.security.repository.users;

import com.app.security.domain.user.Group;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
public interface GroupRepository extends JpaRepository<Group, Long> {
    List<Group> findByNameIn(List<String> name);
}
