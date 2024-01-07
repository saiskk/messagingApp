package com.sakeshet.service.spring.messaging.controller;

import com.sakeshet.service.spring.messaging.model.Message;
import com.sakeshet.service.spring.messaging.requestmodel.MessageRequest;
import com.sakeshet.service.spring.messaging.responseModel.ChatHistoryResponse;
import com.sakeshet.service.spring.messaging.responseModel.MessageResponse;
import com.sakeshet.service.spring.messaging.service.MessageService;
import com.sakeshet.service.spring.messaging.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        if(senderUsername.equals(messageRequest.getTo()))
        {
            return ResponseEntity.badRequest().body("Sender cannot be same as Receiver");
        }
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

    @GetMapping("/{username}/message")
    public ResponseEntity<MessageResponse> getUnreadMessages(@PathVariable String username) {
        List<Message> unreadMessages = messageService.getUnreadMessagesForAUser(username);

        if (!unreadMessages.isEmpty()) {
            return ResponseEntity.ok(new MessageResponse(MessageResponse.Status.SUCCESS, "You have message(s)", unreadMessages));
        } else {
            return ResponseEntity.ok(new MessageResponse(MessageResponse.Status.SUCCESS, "No new messages", null));
        }
    }

    @GetMapping(value = "/{username}/message", params = "friend")
    public ResponseEntity<ChatHistoryResponse> getChatHistory(
            @PathVariable String username,
            @RequestParam String friend) {
        if(username.equals(friend))
        {
            return ResponseEntity.badRequest().body(new ChatHistoryResponse("failure",List.of()));
        }
        List<Message> chatHistory = messageService.getChatHistory(username, friend);
        return ResponseEntity.ok(new ChatHistoryResponse("success", chatHistory));
    }

}

