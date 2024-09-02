package com.drapala.jobrecommendationengine.config;

import io.trino.jdbc.TrinoDriver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

import javax.sql.DataSource;

@Configuration
public class TrinoConfig {

    @Value("${trino.datasource.url}")
    private String url;

    @Value("${trino.datasource.username}")
    private String username;

    @Value("${trino.datasource.password}")
    private String password;

    @Bean
    public DataSource trinoDataSource() {
        SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
        dataSource.setDriverClass(TrinoDriver.class);
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        return dataSource;
    }
}
