package com.drapala.jobrecommendationengine.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerService {

    @KafkaListener(topics = "job-preferences", groupId = "job-recommendation-group")
    public void consumeJobPreferences(String message) {
        System.out.println("Received job preferences: " + message);
        // Add logic to process job preferences and generate recommendations
    }
}