package com.drapala.jobrecommendationengine.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;

@Service
public class TrinoService {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public TrinoService(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<Map<String, Object>> queryTrinoDatabase(String query) {
        return jdbcTemplate.queryForList(query);
    }
}
