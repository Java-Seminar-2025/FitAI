package com.fitai.fitai.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name="workouts")
public class Workout {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne(fetch=FetchType.LAZY,optional = false)
    @JoinColumn(name="plan_id",nullable = false)
    private WorkoutPlan workoutPlan;
    @Column(nullable = false)
    private String name;
    private String day;
}
