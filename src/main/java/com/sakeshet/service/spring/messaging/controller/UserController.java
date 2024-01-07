package com.sakeshet.service.spring.messaging.controller;

import com.sakeshet.service.spring.messaging.model.User;
import com.sakeshet.service.spring.messaging.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class UserController {
    @Autowired UserService userService;
    @PostMapping("/user")
    public ResponseEntity<String> createUser(@Validated @RequestBody User user){
        Optional<User> userOptional = userService.createUser(user);
        if(userOptional.isPresent())
        {
            return ResponseEntity.status(HttpStatus.CREATED).body("User created successfully");
        }
        else {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("User already exists");
        }
    }

    @GetMapping("/user")
    public ResponseEntity<String> getUsers(){
        StringBuilder stringBuilder = new StringBuilder("[");
        userService
                .getUsers()
                .stream()
                .forEach(user -> stringBuilder.append("\"").append(user.getUsername()).append("\","));

        if (stringBuilder.length() > 1) {
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        }
        stringBuilder.append("]");

        return ResponseEntity.ok(stringBuilder.toString());
    }
}
