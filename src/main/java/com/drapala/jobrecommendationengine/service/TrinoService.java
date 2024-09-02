package com.drapala.jobrecommendationengine.service;

import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;

public class TrinoService {

    private final JdbcTemplate jdbcTemplate;

    public TrinoService(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<Map<String, Object>> queryTrinoDatabase() {
        String sql = "SELECT * FROM your_table";
        return jdbcTemplate.queryForList(sql);
    }
}
