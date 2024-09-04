package com.drapala.jobrecommendationengine.controller;

import com.drapala.jobrecommendationengine.entity.Job;
import com.drapala.jobrecommendationengine.repository.JobRepository;
import com.drapala.jobrecommendationengine.service.JobRecommendationService;
import com.drapala.jobrecommendationengine.service.JobAnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/jobs")
public class JobController {

    private final JobRepository jobRepository;
    private final JobRecommendationService jobRecommendationService;
    private final JobAnalysisService jobAnalysisService; // Declaração do novo serviço

    @Autowired
    public JobController(JobRepository jobRepository, JobRecommendationService jobRecommendationService, JobAnalysisService jobAnalysisService) {
        this.jobRepository = jobRepository;
        this.jobRecommendationService = jobRecommendationService;
        this.jobAnalysisService = jobAnalysisService; // Inicializa o novo serviço
    }

    // Endpoint to get all jobs with pagination
    @GetMapping("/all")
    public ResponseEntity<Page<Job>> getAllJobs(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "title") String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        Page<Job> jobs = jobRepository.findAll(pageable);
        return ResponseEntity.ok(jobs);
    }

    // Endpoint to add a new job
    @PostMapping("/add")
    public ResponseEntity<Job> addJob(@Valid @RequestBody Job job) {
        if (job.title() == null || job.description() == null) {
            return ResponseEntity.badRequest().build(); // Validate input
        }
        Job savedJob = jobRepository.save(job);
        return ResponseEntity.ok(savedJob);
    }

    // Endpoint to get job recommendations based on user preferences
    @GetMapping("/recommendations")
    public ResponseEntity<List<Job>> getRecommendations(@RequestParam String userPreferences) {
        if (userPreferences == null || userPreferences.isEmpty()) {
            return ResponseEntity.badRequest().build(); // Validate input
        }
        List<Job> recommendations = jobRecommendationService.recommendJobsBasedOnContent(userPreferences);
        return ResponseEntity.ok(recommendations);
    }

    // Endpoint to get jobs by location with pagination
    @GetMapping("/by-location")
    public ResponseEntity<Page<Job>> getJobsByLocation(
            @RequestParam String location,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "title") String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        Page<Job> jobs = jobRepository.findByLocation(location, pageable);
        return ResponseEntity.ok(jobs);
    }

    // Endpoint to get jobs by salary range with pagination
    @GetMapping("/by-salary-range")
    public ResponseEntity<Page<Job>> getJobsBySalaryRange(
            @RequestParam Double minSalary,
            @RequestParam Double maxSalary,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "title") String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        Page<Job> jobs = jobRepository.findBySalaryBetween(minSalary, maxSalary, pageable);
        return ResponseEntity.ok(jobs);
    }

    // Endpoint to calculate average salary by location using Spark
    @GetMapping("/average-salary-by-location")
    public ResponseEntity<Map<String, Double>> getAverageSalaryByLocation() {
        List<Job> allJobs = jobRepository.findAll();
        if (allJobs.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        Map<String, Double> averageSalaryByLocation = jobAnalysisService.calculateAverageSalaryByLocation(allJobs);
        return ResponseEntity.ok(averageSalaryByLocation);
    }
}
