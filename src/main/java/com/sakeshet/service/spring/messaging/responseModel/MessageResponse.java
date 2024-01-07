package com.sakeshet.service.spring.messaging.responseModel;

import com.sakeshet.service.spring.messaging.model.Message;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MessageResponse {
    private final Status status;
    private final String message;
    private final String data;

    public MessageResponse(Status status, String message, List<Message> messagesList) {
        this.status = status;
        this.message = message;
        if(messagesList==null || messagesList.isEmpty())
        {
            this.data = "[]";
        }
        else {
            Map<String, List<String>> aggregatedMap = new HashMap<>();
            messagesList.forEach(eachMessage -> aggregatedMap.computeIfAbsent(eachMessage.getSenderUsername(), key -> new ArrayList<>())
                    .add(eachMessage.getText()));
            StringBuilder stringBuilder = new StringBuilder("[");
            for (Map.Entry<String, List<String>> entry : aggregatedMap.entrySet()) {
                stringBuilder.append("{")
                        .append("\"username\":\"").append(entry.getKey()).append("\",")
                        .append("\"texts\":").append(convertListToString(entry.getValue()))
                        .append("},");
            }

            if (stringBuilder.length() > 1) {
                stringBuilder.deleteCharAt(stringBuilder.length() - 1);
            }

            stringBuilder.append("]");
            this.data = stringBuilder.toString();
        }
    }

    public Status getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public String getData() {
        return data;
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
