package com.sakeshet.service.spring.messaging.controller;

import com.sakeshet.service.spring.messaging.model.User;
import com.sakeshet.service.spring.messaging.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

import static com.sakeshet.service.spring.messaging.ResponseUtil.failureResponseBody;
import static com.sakeshet.service.spring.messaging.ResponseUtil.successResponseBody;

@RestController
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }
    @PostMapping("/user")
    public ResponseEntity<String> createUser(@Validated @RequestBody User user){
        Optional<User> userOptional = userService.createUser(user);
        if(userOptional.isPresent())
        {
            return ResponseEntity.status(HttpStatus.CREATED).body(successResponseBody);
        }
        else {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(failureResponseBody);
        }
    }

    @GetMapping("/user")
    public ResponseEntity<String> getUsers(){
        StringBuilder stringBuilder = new StringBuilder("[");
        userService
                .getUsers()
                .forEach(user -> stringBuilder.append("\"").append(user.getUsername()).append("\","));

        if (stringBuilder.length() > 1) {
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        }
        stringBuilder.append("]");

        return ResponseEntity.ok(stringBuilder.toString());
    }
}
