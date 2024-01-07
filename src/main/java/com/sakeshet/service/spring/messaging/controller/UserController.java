package com.sakeshet.service.spring.messaging.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sakeshet.service.spring.messaging.model.User;
import com.sakeshet.service.spring.messaging.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
public class UserController {
    private final UserService userService;
    private final ObjectMapper objectMapper;

    public UserController(UserService userService, ObjectMapper objectMapper) {
        this.userService = userService;
        this.objectMapper = objectMapper;
    }
    @PostMapping("/user")
    public ResponseEntity<String> createUser(@Validated @RequestBody User user){
        Optional<User> userOptional = userService.createUser(user);
        Map<String, String> response = new HashMap<>();
        if(userOptional.isPresent())
        {
            response.put("message", "User created successfully");
            response.put("status", "success");
            String finalResponse;
            try {
                finalResponse = objectMapper.writeValueAsString(response);
            }
            catch (JsonProcessingException e) {
                finalResponse = "{\"status\":\"error\",\"message\":\"JSON processing error\"}";
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(finalResponse);
            }
            return ResponseEntity.status(HttpStatus.CREATED).body(finalResponse);
        }
        else {
            response.put("message", "User already exists");
            response.put("status", "failure");
            String finalResponse;
            try {
                finalResponse = objectMapper.writeValueAsString(response);
            }
            catch (JsonProcessingException e) {
                finalResponse = "{\"status\":\"error\",\"message\":\"JSON processing error\"}";
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(finalResponse);
            }
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(finalResponse);
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
