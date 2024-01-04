package com.sakeshet.service.spring.messaging.repository;

import com.sakeshet.service.spring.messaging.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {
    void saveMessage(Message message);

    List<Message> getUnreadMessagesForAUser(String user1);

    List<Message> getChatHistory(String user1, String user2);
}
