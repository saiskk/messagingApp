package com.sakeshet.service.spring.messaging.message;

import com.sakeshet.service.spring.messaging.model.User;
import com.sakeshet.service.spring.messaging.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static com.sakeshet.service.spring.messaging.ResponseUtil.messageSentResponse;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ChatHistoryAPITest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    private String user1 = "user1";
    private String user2 = "user2";
    private String user3 = "user3";
    @BeforeEach
    void setUp() {
        // Initialize the repository with some test users
        userRepository.save(new User(user1,"halo1"));
        userRepository.save(new User(user2,"halo2"));
        userRepository.save(new User(user3,"halo3"));
    }

    @AfterEach
    void tearDown(){
        userRepository.deleteAll();
    }

    @Test
    public void testUnreadMessagesApi() throws Exception {
        String message1 = "Hey bro";
        String message2 = "we up for tonight?";
        String message3 = "Drinks at Macey's right! yes we are on";
        String message4 = "When are you coming, we are waiting";
        // user1 sends messages to user2
        sendMessage(user1, user2, message1);
        sendMessage(user1, user2, message2);
        sendMessage(user2, user1, message3);
        sendMessage(user1, user2, message4);

        String chatHistory = getChatHistory(user1, user2);
        System.out.println(chatHistory);
        Assertions.assertTrue(chatHistory.contains(message1));
        Assertions.assertTrue(chatHistory.contains(message2));
        Assertions.assertTrue(chatHistory.contains(message3));
        Assertions.assertTrue(chatHistory.contains(message4));
    }

    private void sendMessage(String sender, String receiver, String text) throws Exception {
        String requestBody = "{\"to\": \"" + receiver + "\", \"text\": \"" + text + "\"}";

        mockMvc.perform(post("/user/"+sender+"/message")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(content().json(messageSentResponse));
    }

    private String getChatHistory(String username, String friend) throws Exception {
        MvcResult result = mockMvc.perform(get("/user/"+username+"/message?friend="+friend)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        return result.getResponse().getContentAsString();
    }
}

