package com.sakeshet.service.spring.messaging;

public class ResponseUtil {
    public static final String receiverUserNotFoundMessage = "{\"status\":\"error\", \"message\":\"Recipient user not found\"}";
    public static final String messageSentResponse = "{\"status\":\"success\"}";
    public static final String messageNotSentResponse = "{\"status\":\"error\", \"message\":\"Failed to send message\"}";
    public static final String successResponseBody = "{\"message\":\"User created successfully\",\"status\":\"success\"}";
    public static final String failureResponseBody = "{\"message\":\"User already exists\",\"status\":\"failure\"}";
}
