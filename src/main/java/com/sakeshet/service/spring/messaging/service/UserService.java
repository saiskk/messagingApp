package com.sakeshet.service.spring.messaging.service;

import com.sakeshet.service.spring.messaging.model.User;

import java.util.Optional;

public interface UserService {
    Optional<User> createUser(User user);
}
