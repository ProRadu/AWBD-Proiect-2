package com.example.messagingservice.controller;

import com.example.messagingservice.model.Message;
import com.example.messagingservice.service.MessageService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/messages")
public class MessageController {

    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @PostMapping
    public Message sendMessage(@RequestBody Message message) {
        return messageService.sendMessage(message);
    }

    @GetMapping("/conversation")
    public List<Message> getConversation(
            @RequestParam Long user1,
            @RequestParam Long user2) {
        return messageService.getConversation(user1, user2);
    }
}

