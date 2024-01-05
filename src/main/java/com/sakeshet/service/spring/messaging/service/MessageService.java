package com.sakeshet.service.spring.messaging.service;

import com.sakeshet.service.spring.messaging.model.Message;

import java.util.List;

public interface MessageService {
    void saveMessage(Message message);

    List<Message> getUnreadMessagesForAUser(String user1);

    List<Message> getChatHistory(String user1, String user2);
}
