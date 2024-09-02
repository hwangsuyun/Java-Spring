package com.example.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumer {
    @KafkaListener(topics = "${message.topic.name}", groupId = "${spring.kafka.consumer.group-id}")//groupId = ConsumerConfig.GROUP_ID_CONFIG
    //public void consume(String message) throws IOException {
    public void consume(String message) {
        System.out.printf("Consumed message : %s%n", message);
    }
}