package com.drapala.jobrecommendationengine.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
public class PrestoService {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PrestoService(DataSource prestoDataSource) {
        this.jdbcTemplate = new JdbcTemplate(prestoDataSource);
    }

    /**
     * Executes a custom SQL query and returns the result as a list of maps.
     * Each map represents a row, with column names as keys.
     *
     * @param query The SQL query to execute.
     * @return A list of maps representing the query results.
     */
    public List<Map<String, Object>> executeQuery(String query) {
        try {
            validateQuery(query);
            return jdbcTemplate.queryForList(query);
        } catch (DataAccessException e) {
            System.out.println("Error executing query: " + e.getMessage());
            return Collections.emptyList(); // Return an empty list in case of error
        }
    }

    /**
     * Validates the SQL query to prevent SQL injection and other issues.
     *
     * @param query The SQL query to validate.
     * @throws IllegalArgumentException if the query is invalid.
     */
    private void validateQuery(String query) {
        if (query == null || query.trim().isEmpty()) {
            throw new IllegalArgumentException("Query cannot be null or empty.");
        }

        // Additional validation logic can be added here
        // e.g., check for forbidden keywords, length, etc.
        if (query.length() > 1000) { // Example limit for query length
            throw new IllegalArgumentException("Query is too long.");
        }

        // Check for potentially dangerous keywords (basic example)
        String lowerCaseQuery = query.toLowerCase();
        if (lowerCaseQuery.contains("drop ") || lowerCaseQuery.contains("delete ")) {
            throw new IllegalArgumentException("Query contains forbidden keywords.");
        }
    }
}
