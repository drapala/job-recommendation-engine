package com.drapala.jobrecommendationengine.service;

import com.drapala.jobrecommendationengine.entity.Job;
import com.drapala.jobrecommendationengine.repository.JobRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@SpringBootTest
public class JobRecommendationServiceTest {

    @Autowired
    private JobRecommendationService jobRecommendationService;

    @MockBean
    private JobRepository jobRepository;

    @Test
    public void testRecommendJobsBasedOnContent() {
        // Mock the repository's findAll method to return an empty list
        when(jobRepository.findAll()).thenReturn(Collections.emptyList());

        // Call the service method
        List<Job> recommendations = jobRecommendationService.recommendJobsBasedOnContent("Java");

        // Verify the response
        assertTrue(recommendations.isEmpty(), "The recommendations should be empty when no jobs are available.");
    }
}
