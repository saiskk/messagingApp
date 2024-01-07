package com.sakeshet.service.spring.messaging.user;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;
    private static final String requestBodyUser1 = "{\"username\": \"user1\", \"password\": \"halo\"}";
    private static final String requestBodyUser2 = "{\"username\": \"user@_2\", \"password\": \"halo\"}";
    private static final String requestBodyDuplicateUser2 = "{\"username\": \"user@_2\", \"password\": \"h@@lo\"}";
    @Test
    void testUserControllerBehaviour() throws  Exception{
        mockMvc.perform(get("/user"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));

        mockMvc.perform(post("/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBodyUser1))
                .andExpect(status().isCreated())
                .andExpect(content().string("User created successfully"));

        mockMvc.perform(post("/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBodyUser1))
                .andExpect(status().isConflict())
                .andExpect(content().string("User already exists"));

        mockMvc.perform(get("/user"))
                .andExpect(status().isOk())
                .andExpect(content().json("[\"user1\"]"));

        mockMvc.perform(post("/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBodyUser2))
                .andExpect(status().isCreated())
                .andExpect(content().string("User created successfully"));

        mockMvc.perform(get("/user"))
                .andExpect(status().isOk())
                .andExpect(content().json("[\"user1\",\"user@_2\"]"));

        mockMvc.perform(post("/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBodyDuplicateUser2))
                .andExpect(status().isConflict())
                .andExpect(content().string("User already exists"));

        mockMvc.perform(get("/user"))
                .andExpect(status().isOk())
                .andExpect(content().json("[\"user1\",\"user@_2\"]"));
    }
}

