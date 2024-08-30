package com.drapala.jobrecommendationengine.serivce;

import com.drapala.jobrecommendationengine.entity.Job;
import com.drapala.jobrecommendationengine.repository.JobRepository;
import com.drapala.jobrecommendationengine.service.JobRecommendationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.when;

@SpringBootTest
public class JobRecommendationServiceTest {

    @Autowired
    private JobRecommendationService jobRecommendationService;

    @MockBean
    private JobRepository jobRepository;

    @Test
    public void testRecommendJobsBasedOnContent() {
        Job job1 = new Job(null, "Java Developer", "Develop Java applications", "Tech Corp", "New York", "Java, Spring Boot", "100000");
        Job job2 = new Job(null, "Python Developer", "Develop Python applications", "Data Corp", "San Francisco", "Python, Django", "120000");

        when(jobRepository.findAll()).thenReturn(List.of(job1, job2));

        String userPreferences = "Java Developer";
        List<Job> recommendations = jobRecommendationService.recommendJobsBasedOnContent(userPreferences);

        assertFalse(recommendations.isEmpty());
    }
}
