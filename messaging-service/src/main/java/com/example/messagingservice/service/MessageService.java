package com.example.messagingservice.service;

import com.example.messagingservice.exception.UserNotFoundException;
import com.example.messagingservice.model.Message;
import com.example.messagingservice.model.dto.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.example.messagingservice.repository.MessageRepository;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MessageService {

    private final MessageRepository messageRepository;
    private final RestTemplate restTemplate;

    public MessageService(MessageRepository messageRepository, RestTemplate restTemplate) {
        this.messageRepository = messageRepository;
        this.restTemplate = restTemplate;
    }

    public Message sendMessage(Message message) {
        if (!userExists(message.getSenderId()) || !userExists(message.getReceiverId())) {
            throw new UserNotFoundException("Unul dintre utilizatori nu exista.");
        }

        message.setTimestamp(LocalDateTime.now());
        return messageRepository.save(message);
    }


    public List<Message> getConversation(Long user1, Long user2) {
        return messageRepository.findBySenderIdAndReceiverIdOrReceiverIdAndSenderId(
                user1, user2, user1, user2
        );
    }

    public boolean userExists(Long userId) {
        String userServiceUrl = "http://user-service/api/users/" + userId;
        try {
            ResponseEntity<User> response = restTemplate.getForEntity(userServiceUrl, User.class);
            return response.getStatusCode() == HttpStatus.OK;
        } catch (HttpClientErrorException.NotFound e) {
            return false;
        }
    }

}

