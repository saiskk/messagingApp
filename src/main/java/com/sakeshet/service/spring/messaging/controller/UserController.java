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
            return ResponseEntity.ok("User created successfully");
        }
        else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("User already exists");
        }
    }

    @GetMapping("/user")
    public ResponseEntity<String> getUsers(){
        List<String> usernameList = userService.getUsers().stream().map(User::getUsername).collect(Collectors.toList());

        StringBuilder stringBuilder = new StringBuilder("[");
        for (String username : usernameList) {
            stringBuilder.append("\"").append(username).append("\",");
        }

        if (!usernameList.isEmpty()) {
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        }

        stringBuilder.append("]");

        String result = stringBuilder.toString();
        return ResponseEntity.ok(result);
    }
}
