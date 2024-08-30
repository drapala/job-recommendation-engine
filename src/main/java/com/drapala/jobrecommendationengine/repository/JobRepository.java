package com.drapala.jobrecommendationengine.repository;

import com.drapala.jobrecommendationengine.entity.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobRepository extends JpaRepository<Job, Long> {
    // Define custom query methods if needed
}
