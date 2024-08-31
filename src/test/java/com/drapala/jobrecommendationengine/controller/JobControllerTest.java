package com.drapala.jobrecommendationengine.controller;

import com.drapala.jobrecommendationengine.entity.Job;
import com.drapala.jobrecommendationengine.repository.JobRepository;
import com.drapala.jobrecommendationengine.service.JobRecommendationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.Collections;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(JobController.class)
public class JobControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JobRepository jobRepository;

    @MockBean
    private JobRecommendationService jobRecommendationService;

    @Test
    public void testGetAllJobs() throws Exception {
        // Mock an empty Page object
        Page<Job> emptyPage = new PageImpl<>(Collections.emptyList());

        // Mock the repository's findAll method to return the empty page
        when(jobRepository.findAll(PageRequest.of(0, 10, Sort.by("title")))).thenReturn(emptyPage);

        // Perform a GET request to the /api/jobs/all endpoint with default pagination parameters and verify the response
        mockMvc.perform(get("/api/jobs/all")
                        .param("page", "0")
                        .param("size", "10")
                        .param("sortBy", "title"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isEmpty()); // Verify the "content" field is empty
    }
}
