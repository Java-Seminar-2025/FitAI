package com.fitai.fitai.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fitai.fitai.model.Exercise;

public interface ExerciseRepository extends JpaRepository<Exercise,Integer> {}