package com.fitai.fitai.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name="fitness_profiles")
@Data
public class FitnessProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name="user_id",nullable=false,unique = true)
    private User user;
    private String experienceLevel;
    private String activityLevel;
    private Integer trainingDays;
}
