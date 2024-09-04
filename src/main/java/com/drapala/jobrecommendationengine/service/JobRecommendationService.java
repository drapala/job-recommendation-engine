package com.drapala.jobrecommendationengine.service;

import com.drapala.jobrecommendationengine.entity.Job;
import com.drapala.jobrecommendationengine.repository.JobRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.text.similarity.CosineSimilarity;
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

    private final JobRepository jobRepository;
    private final ObjectMapper objectMapper;
    private final CosineSimilarity cosineSimilarity;

    @Autowired
    public JobRecommendationService(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
        this.objectMapper = new ObjectMapper();
        this.cosineSimilarity = new CosineSimilarity();
    }

    @Cacheable("jobRecommendations")
    public List<Job> recommendJobsBasedOnContent(String userPreferences) {
        List<Job> allJobs = jobRepository.findAll();

        return allJobs.stream()
                .sorted((job1, job2) -> Double.compare(
                        calculateSimilarity(userPreferences, job2),
                        calculateSimilarity(userPreferences, job1)
                ))
                .collect(Collectors.toList());
    }

    private double calculateSimilarity(String userPreferences, Job job) {
        String combinedJobData = job.title() + " " + job.description() + " " + job.company() + " " + job.location();

        Map<CharSequence, Integer> vector1 = getVector(userPreferences);
        Map<CharSequence, Integer> vector2 = getVector(combinedJobData.toLowerCase());

        return cosineSimilarity.cosineSimilarity(vector1, vector2);
    }

    private Map<CharSequence, Integer> getVector(String text) {
        Map<CharSequence, Integer> termFrequency = new HashMap<>();
        String[] terms = text.toLowerCase().split("\\s+");
        for (String term : terms) {
            termFrequency.put(term, termFrequency.getOrDefault(term, 0) + 1);
        }
        return termFrequency;
    }

    @KafkaListener(topics = "user-events", groupId = "job-recommendation-group")
    public void consumeUserEvent(String message) {
        try {
            Map<String, String> userEvent = objectMapper.readValue(message, new TypeReference<Map<String, String>>() {});
            String userPreferences = userEvent.get("preferences"); // Assuming message contains "preferences" key

            List<Job> recommendations = recommendJobsBasedOnContent(userPreferences);

            System.out.println("Generated recommendations for user: " + recommendations);
        } catch (Exception e) {
            System.out.println("Error processing user event: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
