package com.fitai.fitai.model;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name="exercises")
@Data
public class Exercise {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String muscleGroup;
    @Column(length=1000)
    private String description;
    private String equipment;
}
