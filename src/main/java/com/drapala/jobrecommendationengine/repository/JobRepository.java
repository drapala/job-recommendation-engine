package com.drapala.jobrecommendationengine.repository;

import com.drapala.jobrecommendationengine.entity.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobRepository extends JpaRepository<Job, Long> {

    List<Job> findByTitle(String title);

    List<Job> findByCompany(String company);
}
