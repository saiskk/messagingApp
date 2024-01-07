package com.sakeshet.service.spring.messaging.service.impl;

import com.sakeshet.service.spring.messaging.model.User;
import com.sakeshet.service.spring.messaging.repository.UserRepository;
import com.sakeshet.service.spring.messaging.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public Optional<User> createUser(User user){
        if(userRepository.existsByUsername(user.getUsername()))
        {
            return Optional.empty();
        }
        else {
            return Optional.of(userRepository.save(user));
        }
    }

    @Override
    public List<User> getUsers(){
        return userRepository.findAll();
    }
}
