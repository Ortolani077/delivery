package com.security.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.security.Model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
}
