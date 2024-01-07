package com.sakeshet.service.spring.messaging.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Index;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "messages", indexes = {
        @Index(name = "idx_sender_receiver", columnList = "senderUsername, receiverUsername")
})
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String senderUsername;

    @Column(nullable = false)
    private String receiverUsername;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String text;

    @Column(nullable = false)
    private LocalDateTime timestamp;

    @Column(nullable = false)
    private boolean read;

    public Message() {
        this.timestamp = LocalDateTime.now();
    }

    public Message(String senderUsername, String receiverUsername, String text) {
        this();
        this.senderUsername = senderUsername;
        this.receiverUsername = receiverUsername;
        this.text = text;
        this.read = false;
    }

    public Long getId() {
        return id;
    }

    public String getSenderUsername() {
        return senderUsername;
    }

    public String getReceiverUsername() {
        return receiverUsername;
    }

    public String getText() {
        return text;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void markAsRead()
    {
        this.read = true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Message message = (Message) o;
        return Objects.equals(senderUsername, message.senderUsername) &&
                Objects.equals(receiverUsername, message.receiverUsername) &&
                Objects.equals(text, message.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(senderUsername, receiverUsername, text);
    }
}
