package com.sakeshet.service.spring.messaging.responseModel;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sakeshet.service.spring.messaging.model.Message;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatHistoryResponse implements Serializable{
    @JsonProperty("status")
    private final String status;
    @JsonProperty("texts")
    private final List<Map<String, String>> texts;

    public ChatHistoryResponse(String status, List<Message> chatHistory) {
        this.status = status;
        this.texts = formatMessages(chatHistory);
    }

    private List<Map<String, String>> formatMessages(List<Message> messages) {
        List<Map<String, String>> formattedMessages = new ArrayList<>();
        messages.forEach(message -> {
            Map<String, String> messageMap = new HashMap<>();
            messageMap.put(message.getSenderUsername(), message.getText());
            formattedMessages.add(messageMap);
        });
        return formattedMessages;
    }

    @Override
    public String toString() {
        return String.format("Response: {\"status\":\"%s\", \"texts\":%s}", status, texts);
    }
}
