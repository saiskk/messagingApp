package com.sakeshet.service.spring.messaging.service.impl;

import com.sakeshet.service.spring.messaging.model.Message;
import com.sakeshet.service.spring.messaging.repository.MessageRepository;
import com.sakeshet.service.spring.messaging.service.MessageService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {
    private final MessageRepository messageRepository;

    public MessageServiceImpl(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public boolean sendMessage(String senderUsername, String receiverUsername, String messageBody) {
        messageRepository.save(new Message(senderUsername, receiverUsername, messageBody));
        return true; // Assuming the message was sent successfully
    }

    @Override
    public List<Message> getUnreadMessagesForAUser(String user1) {
        return null;
    }

    @Override
    public List<Message> getChatHistory(String user1, String user2) {
        return null;
    }
}
