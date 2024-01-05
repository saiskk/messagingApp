package com.sakeshet.service.spring.messaging.repository;

import com.sakeshet.service.spring.messaging.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByUsername(String username);
}
