package com.example.messagingservice.repository;

import com.example.messagingservice.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findBySenderIdAndReceiverIdOrReceiverIdAndSenderId(
            Long senderId, Long receiverId, Long receiverId2, Long senderId2);
}

