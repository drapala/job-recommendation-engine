package com.drapala.jobrecommendationengine.service;

import com.drapala.jobrecommendationengine.entity.Job;
import com.drapala.jobrecommendationengine.repository.JobRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.text.similarity.CosineSimilarity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class JobRecommendationService {

    private static final Logger logger = LoggerFactory.getLogger(JobRecommendationService.class);

    @Autowired
    private JobRepository jobRepository;

    private final ObjectMapper objectMapper = new ObjectMapper(); // For JSON parsing

    // Recomendação de empregos baseada em conteúdo (Content-Based Filtering)
    @Cacheable("jobRecommendations")
    public List<Job> recommendJobsBasedOnContent(String userPreferences) {
        List<Job> allJobs = jobRepository.findAll();

        // Calcular a similaridade entre as preferências do usuário e as descrições de emprego
        return allJobs.stream()
                .sorted((job1, job2) -> Double.compare(
                        calculateSimilarity(userPreferences, job2),
                        calculateSimilarity(userPreferences, job1)
                ))
                .collect(Collectors.toList());
    }

    // Método para calcular a similaridade entre as preferências do usuário e o emprego
    private double calculateSimilarity(String userPreferences, Job job) {
        String combinedJobData = job.title() + " " + job.description() + " " + job.company() + " " + job.location();
        CosineSimilarity cosineSimilarity = new CosineSimilarity();
        Map<CharSequence, Integer> vector1 = getVector(userPreferences);
        Map<CharSequence, Integer> vector2 = getVector(combinedJobData.toLowerCase());
        return cosineSimilarity.cosineSimilarity(vector1, vector2);
    }

    // Converter texto em um vetor de frequência de termos
    private Map<CharSequence, Integer> getVector(String text) {
        Map<CharSequence, Integer> termFrequency = new HashMap<>();
        String[] terms = text.toLowerCase().split("\\s+");
        for (String term : terms) {
            termFrequency.put(term, termFrequency.getOrDefault(term, 0) + 1);
        }
        return termFrequency;
    }

    // Consumir eventos de usuário do Kafka
    @KafkaListener(topics = "user-events", groupId = "job-recommendation-group")
    public void consumeUserEvent(String message) {
        try {
            // Parse JSON message to extract user preferences
            Map<String, String> userEvent = objectMapper.readValue(message, Map.class);
            String userPreferences = userEvent.get("preferences"); // Assuming message contains "preferences" key

            // Generate recommendations based on parsed user preferences
            List<Job> recommendations = recommendJobsBasedOnContent(userPreferences);

            // Process recommendations, e.g., store them or send them to another service
            logger.info("Generated recommendations for user: {}", recommendations);
        } catch (Exception e) {
            logger.error("Error processing user event: {}", e.getMessage(), e);
        }
    }
}
