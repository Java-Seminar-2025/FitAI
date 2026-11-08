package com.fitai.fitai.controller;
import org.springframework.web.bind.annotation.*;

import com.fitai.fitai.model.Exercise;
import com.fitai.fitai.repository.ExerciseRepository;

import java.util.List;

@RestController
@RequestMapping("/api/exercises")
public class ExerciseController {
    private ExerciseRepository repo;
    public ExerciseController(ExerciseRepository repo){
        this.repo=repo;
    }
    @GetMapping
    public List<Exercise> all() {return repo.findAll();}
    @PostMapping
    public Exercise add(@RequestBody Exercise e) {return repo.save(e);}
}
