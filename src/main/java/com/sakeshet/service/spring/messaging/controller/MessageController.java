package com.sakeshet.service.spring.messaging.controller;

import com.sakeshet.service.spring.messaging.requestmodel.MessageRequest;
import com.sakeshet.service.spring.messaging.service.MessageService;
import com.sakeshet.service.spring.messaging.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.sakeshet.service.spring.messaging.ResponseUtil.messageSentResponse;
import static com.sakeshet.service.spring.messaging.ResponseUtil.messageNotSentResponse;
import static com.sakeshet.service.spring.messaging.ResponseUtil.receiverUserNotFoundMessage;

@RestController
@RequestMapping("/user")
public class MessageController {
    private final MessageService messageService;
    private final UserService userService;

    public MessageController(MessageService messageService, UserService userService) {
        this.messageService = messageService;
        this.userService = userService;
    }

    @PostMapping("/{senderUsername}/message")
    public ResponseEntity<String> sendMessage(
            @PathVariable String senderUsername,
            @RequestBody MessageRequest messageRequest) {
        if(!userService.existsUser(messageRequest.getTo()))
        {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(receiverUserNotFoundMessage);
        }
        boolean messageSent = messageService
                .sendMessage(senderUsername, messageRequest.getTo(), messageRequest.getText());

        if (messageSent) {
            return ResponseEntity.ok(messageSentResponse);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(messageNotSentResponse);
        }
    }
}

