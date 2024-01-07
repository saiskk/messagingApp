package com.sakeshet.service.spring.messaging.repository;

import com.sakeshet.service.spring.messaging.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByUsername(String username);
}
