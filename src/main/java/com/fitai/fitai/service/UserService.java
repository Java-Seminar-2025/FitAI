package com.fitai.fitai.service;

import com.fitai.fitai.dto.RegisterRequest;
import com.fitai.fitai.dto.UpdateUserRequest;
import com.fitai.fitai.dto.UserResponse;
import com.fitai.fitai.exception.DuplicateResourceException;
import com.fitai.fitai.exception.ResourceNotFoundException;
import com.fitai.fitai.model.User;
import com.fitai.fitai.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

   private final UserRepository userRepository;
   private final PasswordEncoder passwordEncoder;

   public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
      this.userRepository = userRepository;
      this.passwordEncoder = passwordEncoder;
   }

   public UserResponse register(RegisterRequest request) {
      if (userRepository.existsByUsername(request.username())) {
         throw new DuplicateResourceException("Username already taken");
      }
      if (userRepository.existsByEmail(request.email())) {
         throw new DuplicateResourceException("Email already registered");
      }
      User user = new User();
      user.setUsername(request.username());
      user.setEmail(request.email());
      user.setPassword(passwordEncoder.encode(request.password()));
      return toResponse(userRepository.save(user));
   }

   public List<UserResponse> getAll() {
      return userRepository.findAll().stream().map(this::toResponse).toList();
   }

   public UserResponse getById(Integer id) {
      return toResponse(findById(id));
   }

   public UserResponse getByUsername(String username) {
      return toResponse(findByUsername(username));
   }

   public UserResponse updateByUsername(String username, UpdateUserRequest request) {
      User user = findByUsername(username);

      if (request.email() != null && !request.email().isBlank()
              && !request.email().equals(user.getEmail())) {
         if (userRepository.existsByEmail(request.email())) {
            throw new DuplicateResourceException("Email already registered");
         }
         user.setEmail(request.email());
      }
      if (request.password() != null && !request.password().isBlank()) {
         user.setPassword(passwordEncoder.encode(request.password()));
      }
      return toResponse(userRepository.save(user));
   }

   public void deleteById(Integer id) {
      userRepository.delete(findById(id));
   }

   public void deleteByUsername(String username) {
      userRepository.delete(findByUsername(username));
   }

   private User findById(Integer id) {
      return userRepository.findById(id)
              .orElseThrow(() -> new ResourceNotFoundException("User not found: " + id));
   }

   private User findByUsername(String username) {
      return userRepository.findByUsername(username)
              .orElseThrow(() -> new ResourceNotFoundException("User not found: " + username));
   }

   private UserResponse toResponse(User u) {
      return new UserResponse(u.getId(), u.getUsername(), u.getEmail(), u.getRole(), u.getCreatedAt());
   }
}