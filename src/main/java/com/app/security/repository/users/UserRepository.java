package com.app.security.repository.users;

import com.app.security.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

Optional<User>findByUserCode(String userCode);
    @Override
    void delete(User user);
}
