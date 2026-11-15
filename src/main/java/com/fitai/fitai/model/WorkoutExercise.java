package com.fitai.fitai.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name="workout_exercises")

public class WorkoutExercise {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne(fetch=FetchType.LAZY,optional = false)
    @JoinColumn(name="workout_id",nullable=false)
    private Workout workout;
    @ManyToOne(fetch = FetchType.LAZY,optional=false)
    @JoinColumn(name="exercise_id",nullable=false)
    private Exercise exercise;
    private Integer sets;
    private Integer reps;
}
