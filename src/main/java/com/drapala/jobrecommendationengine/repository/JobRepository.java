package com.drapala.jobrecommendationengine.repository;

import com.drapala.jobrecommendationengine.entity.Job;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobRepository extends JpaRepository<Job, Long> {

    // Find jobs by title
    List<Job> findByTitle(String title);

    // Find jobs by company
    List<Job> findByCompany(String company);

    // Find jobs by location
    List<Job> findByLocation(String location);

    // Find jobs by salary range
    List<Job> findBySalaryBetween(Double minSalary, Double maxSalary);

    // Find jobs by title with pagination
    Page<Job> findByTitle(String title, Pageable pageable);

    // Find jobs by company with pagination
    Page<Job> findByCompany(String company, Pageable pageable);

    // Find jobs by location with pagination
    Page<Job> findByLocation(String location, Pageable pageable);

    // Find jobs by salary range with pagination
    Page<Job> findBySalaryBetween(Double minSalary, Double maxSalary, Pageable pageable);
}
