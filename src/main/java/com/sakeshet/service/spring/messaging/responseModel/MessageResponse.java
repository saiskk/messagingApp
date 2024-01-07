package com.sakeshet.service.spring.messaging.responseModel;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sakeshet.service.spring.messaging.model.Message;

import java.util.*;

public class MessageResponse {
    @JsonProperty("status")
    private final Status status;
    @JsonProperty("message")
    private final String message;
    @JsonProperty("data")
    private final List<Map<String, Object>> data;

    public MessageResponse(Status status, String message, List<Message> messagesList) {
        this.status = status;
        this.message = message;
        this.data = convertMessagesToMap(messagesList);
    }

    private List<Map<String, Object>> convertMessagesToMap(List<Message> messagesList) {
        if (messagesList == null || messagesList.isEmpty()) {
            return Collections.emptyList();
        }

        Map<String, List<String>> aggregatedMap = new HashMap<>();

        // Aggregate messages based on sender's username
        messagesList.forEach(eachMessage ->
                aggregatedMap.computeIfAbsent(eachMessage.getSenderUsername(), key -> new ArrayList<>())
                        .add(eachMessage.getText())
        );

        List<Map<String, Object>> formattedMessages = new ArrayList<>();

        // Convert aggregatedMap entries to the desired format
        aggregatedMap.forEach((username, texts) -> {
            Map<String, Object> messageMap = new HashMap<>();
            messageMap.put("username", username);
            messageMap.put("texts", texts);
            formattedMessages.add(messageMap);
        });

        return formattedMessages;
    }

    public Status getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    private static String convertListToString(List<String> list) {
        StringBuilder listString = new StringBuilder("[");

        for (String item : list) {
            listString.append("\"").append(item).append("\",");
        }

        if (listString.length() > 1) {
            listString.deleteCharAt(listString.length() - 1);
        }

        listString.append("]");

        return listString.toString();
    }

    public enum Status {
        SUCCESS,
        FAILURE
    }

    @Override
    public String toString() {
        return String.format("Response: {\"status\":\"%s\", \"message\":\"%s\", \"data\": %s}", status.toString().toLowerCase(), message, data);
    }
}
