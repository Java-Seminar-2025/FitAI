package com.fitai.fitai.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name="workout_plans")

public class WorkoutPlan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne(fetch =FetchType.LAZY,optional=false)
    @JoinColumn(name="user_id",nullable = false)
    private User user;
    @Column(nullable = false)
    private String name;
    private Boolean userGenerated;


}
