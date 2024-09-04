package com.drapala.jobrecommendationengine.service;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JobDataProcessingService {
    private final SparkSession sparkSession;

    @Autowired
    public JobDataProcessingService(SparkSession sparkSession) {
        this.sparkSession = sparkSession;
    }

    public void processJobData() {
        Dataset<Row> jobsData = sparkSession.read().format("csv")
                .option("header", "true")
                .load("path/to/jobs.csv");

        jobsData.createOrReplaceTempView("jobs");
        Dataset<Row> result = sparkSession.sql("SELECT title, location, salary FROM jobs WHERE salary > 50000");

        result.show();
    }
}
