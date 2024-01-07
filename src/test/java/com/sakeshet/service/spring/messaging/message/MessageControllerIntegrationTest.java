package com.sakeshet.service.spring.messaging.message;

import com.sakeshet.service.spring.messaging.model.User;
import com.sakeshet.service.spring.messaging.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static com.sakeshet.service.spring.messaging.ResponseUtil.messageSentResponse;
import static com.sakeshet.service.spring.messaging.ResponseUtil.receiverUserNotFoundMessage;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class MessageControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository; // Assuming you have a UserRepository

    @BeforeEach
    void setUp() {
        // Initialize the repository with some test users
        userRepository.save(new User("johnsmith007","halo1"));
        userRepository.save(new User("dummy009","halo2"));
    }

    @AfterEach
    void tearDown(){
        userRepository.deleteAll();
    }

    @Test
    void testSendMessage() throws Exception {
        String requestBody = "{\"to\": \"dummy009\", \"text\": \"no problem\"}";

        mockMvc.perform(post("/user/johnsmith007/message")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(content().json(messageSentResponse));
    }

    @Test
    void testSendMessageToNonExistingUser() throws Exception {
        String requestBody = "{\"to\": \"nonexistentuser\", \"text\": \"hello\"}";

        mockMvc.perform(post("/user/johnsmith007/message")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isNotFound())
                .andExpect(content().json(receiverUserNotFoundMessage));
    }
}
