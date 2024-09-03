package com.drapala.jobrecommendationengine.kafka;

import com.drapala.jobrecommendationengine.service.TrinoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerService {

    private final TrinoService trinoService;

    @Autowired
    public KafkaConsumerService(TrinoService trinoService) {
        this.trinoService = trinoService;
    }

    @KafkaListener(topics = "job-preferences", groupId = "job-recommendation-group")
    public void consumeJobPreferences(String message) {
        System.out.println("Received job preferences: " + message);

        // Process the job preferences
        processJobPreferences(message);
    }

    private void processJobPreferences(String message) {
        // Add your logic to parse and process the job preferences
        // Example: extract user ID, job preferences, etc.

        // Call Trino to perform an analytical query
        String queryResult = trinoService.runQuery("SELECT * FROM job_data WHERE preference = 'example';");

        // Use the query result to adjust recommendations
        System.out.println("Trino query result: " + queryResult);

        // Additional logic to handle the result and generate job recommendations
    }
}
