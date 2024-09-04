package com.drapala.jobrecommendationengine.kafka;

import com.drapala.jobrecommendationengine.service.PrestoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class KafkaConsumerService {

    private final PrestoService prestoService;

    @Autowired
    public KafkaConsumerService(PrestoService prestoService) {
        this.prestoService = prestoService;
    }

    @KafkaListener(topics = "job-preferences", groupId = "job-recommendation-group")
    public void consumeJobPreferences(String message) {
        System.out.println("Received job preferences: " + message);
        processJobPreferences(message);
    }

    private void processJobPreferences(String message) {
        if (message == null || message.trim().isEmpty()) {
            System.out.println("Received an empty or null message.");
            return;
        }

        try {
            String query = "SELECT * FROM job_data WHERE preference = '" + message + "';";
            System.out.println("Executing query: " + query);

            List<Map<String, Object>> queryResult = prestoService.executeQuery(query);

            if (queryResult.isEmpty()) {
                System.out.println("No results found for preference: " + message);
            } else {
                System.out.println("Presto query result for preference '" + message + "': " + queryResult);
            }
        } catch (Exception e) {
            System.out.println("Error processing job preferences: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
