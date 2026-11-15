package com.fitai.fitai.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name="workouts_history")
public class WorkoutHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne (fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name="user_id", nullable = false)
    private User user;
    @ManyToOne (fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name="workout_id", nullable = false)
    private Workout workout;
    @ManyToOne (fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name="exercise_id", nullable = false)
    private Exercise exercise;
    @Column(name = "completed_at")
    private LocalDateTime completedAt = LocalDateTime.now();
    private Integer duration;
    @Column(length = 1000)
    private String notes;


}
