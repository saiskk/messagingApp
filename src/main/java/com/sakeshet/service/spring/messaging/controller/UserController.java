package com.sakeshet.service.spring.messaging.controller;

import com.sakeshet.service.spring.messaging.model.User;
import com.sakeshet.service.spring.messaging.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class UserController {
    @Autowired UserService userService;
    @PostMapping
    public ResponseEntity<String> createUser(@Validated @RequestBody User user){
        Optional<User> userOptional = userService.createUser(user);
        if(userOptional.isPresent())
        {
            return ResponseEntity.ok("User created successfully");
        }
        else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("User already exists");
        }
    }
}
