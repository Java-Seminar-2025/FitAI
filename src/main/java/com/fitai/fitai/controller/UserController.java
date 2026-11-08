package com.fitai.fitai.controller;

import com.fitai.fitai.model.User;
import com.fitai.fitai.repository.UserRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController 
@RequestMapping("api/users")
public class UserController {
    
    private final UserRepository userRepository;

    // prik ovoga Spring automatski ubacuje (inject) repozitorij
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // vraća listu svih korisnika
    @GetMapping
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // sprema novog korisnika u bazu
    @PostMapping
    public User createUser(@RequestBody User user) {
        return userRepository.save(user);
    }
}
