package com.drapala.jobrecommendationengine.service;

import com.drapala.jobrecommendationengine.entity.Job;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class JobAnalysisService {

    private final SparkSession sparkSession;

    @Autowired
    public JobAnalysisService(SparkSession sparkSession) {
        this.sparkSession = sparkSession;
    }

    public Map<String, Double> calculateAverageSalaryByLocation(List<Job> jobs) {
        // Convert the list of jobs to a Spark Dataset
        Dataset<Row> jobsData = sparkSession.createDataFrame(jobs, Job.class);

        // Register the Dataset as a temporary table
        jobsData.createOrReplaceTempView("jobs");

        // Use Spark SQL to calculate the average salary by location
        Dataset<Row> averageSalaries = sparkSession.sql(
                "SELECT location, AVG(salary) as average_salary FROM jobs GROUP BY location"
        );

        // Convert the results to a map
        Map<String, Double> averageSalaryByLocation = averageSalaries.collectAsList().stream()
                .collect(Collectors.toMap(
                        row -> row.getString(0), // Location
                        row -> row.getDouble(1)  // Average salary
                ));

        return averageSalaryByLocation;
    }
}
