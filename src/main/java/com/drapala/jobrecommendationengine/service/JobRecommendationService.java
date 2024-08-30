package com.drapala.jobrecommendationengine.service;

import com.drapala.jobrecommendationengine.entity.Job;
import com.drapala.jobrecommendationengine.repository.JobRepository;
import org.apache.commons.text.similarity.CosineSimilarity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class JobRecommendationService {

    @Autowired
    private JobRepository jobRepository;

    // Recomendação de empregos baseada em conteúdo (Content-Based Filtering)
    public List<Job> recommendJobsBasedOnContent(String userPreferences) {
        List<Job> allJobs = jobRepository.findAll();

        // Calcular a similaridade entre as preferências do usuário e as descrições de emprego
        return allJobs.stream()
                .sorted((job1, job2) -> Double.compare(
                        calculateSimilarity(userPreferences, job2.description()),
                        calculateSimilarity(userPreferences, job1.description())
                ))
                .collect(Collectors.toList());
    }

    // Método auxiliar para calcular a similaridade entre as preferências do usuário e a descrição do emprego
    private double calculateSimilarity(String text1, String text2) {
        CosineSimilarity cosineSimilarity = new CosineSimilarity();
        Map<CharSequence, Integer> vector1 = getVector(text1);
        Map<CharSequence, Integer> vector2 = getVector(text2);
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
        System.out.println("Received event: " + message);
        // Implementar lógica de processamento de eventos de usuário aqui
    }
}
