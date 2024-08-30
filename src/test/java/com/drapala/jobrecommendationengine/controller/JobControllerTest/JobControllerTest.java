package com.drapala.jobrecommendationengine.controller.JobControllerTest;

import com.drapala.jobrecommendationengine.controller.JobController;
import com.drapala.jobrecommendationengine.repository.JobRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(JobController.class)
@ActiveProfiles("test") // Specify the 'test' profile to use the test configuration
public class JobControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JobRepository jobRepository;

    @Test
    public void testGetAllJobs() throws Exception {
        when(jobRepository.findAll()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/jobs/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty());
    }
}
