package com.example.binarfud.kafka.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class MessageConsumer {

    @KafkaListener(topics = "new-merchant", groupId = "group-id")
    public void listen(String message) {
        System.out.println("Message received: " + message);
    }
}
