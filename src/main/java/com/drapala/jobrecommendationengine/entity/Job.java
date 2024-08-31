package com.drapala.jobrecommendationengine.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;

@Entity
@Table(name = "jobs")
public record Job(

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        Long id,

        @Column(nullable = false)
        String title,

        String description,
        String company,
        String location,
        String requirements,
        Double salary

) {}
