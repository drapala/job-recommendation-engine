package com.drapala.jobrecommendationengine.config;

import com.facebook.presto.jdbc.PrestoDriver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

import javax.sql.DataSource;

@Configuration
public class PrestoConfig {

    @Value("${presto.datasource.url}")
    private String url;

    @Value("${presto.datasource.username}")
    private String username;

    @Value("${presto.datasource.password}")
    private String password;

    @Bean
    public DataSource prestoDataSource() {
        SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
        dataSource.setDriverClass(PrestoDriver.class);
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        return dataSource;
    }
}
