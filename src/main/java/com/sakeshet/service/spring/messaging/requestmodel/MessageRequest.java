package com.sakeshet.service.spring.messaging.requestmodel;

import java.io.Serializable;

public class MessageRequest implements Serializable {
    String to;
    String text;

    public MessageRequest(String to, String text) {
        this.to = to;
        this.text = text;
    }
    public String getTo() {
        return to;
    }

    public String getText() {
        return text;
    }
}
