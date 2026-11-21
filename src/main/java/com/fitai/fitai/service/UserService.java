package com.fitai.fitai.service;

import com.fitai.fitai.dto.RegisterRequest;
import com.fitai.fitai.dto.UpdateUserRequest;
import com.fitai.fitai.dto.UserResponse;
import com.fitai.fitai.exception.DuplicateResourceException;
import com.fitai.fitai.exception.ResourceNotFoundException;
import com.fitai.fitai.model.User;
import com.fitai.fitai.repository.UserRepository;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

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

   public UserResponse getByUuid(UUID uuid) {
      return toResponse(findByUuid(uuid));
   }

   public UserResponse getByUsername(String username) {
      return toResponse(findByUsername(username));
   }

   public UserResponse updateByUsername(String username, UpdateUserRequest request) {
      return updateUser(findByUsername(username), request);
   }

   public UserResponse updateByUuid(UUID uuid, UpdateUserRequest request, Authentication authentication) {
      User user = findByUuid(uuid);
      checkOwner(user, authentication);
      return updateUser(user, request);
   }

   public void deleteByUsername(String username) {
      userRepository.delete(findByUsername(username));
   }

   public void deleteByUuid(UUID uuid, Authentication authentication) {
      User user = findByUuid(uuid);
      checkOwner(user, authentication);
      userRepository.delete(user);
   }

   private UserResponse updateUser(User user, UpdateUserRequest request) {
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

   private void checkOwner(User user, Authentication authentication) {
      boolean isAdmin=authentication.getAuthorities().stream().anyMatch(
              auth->auth.getAuthority().equals("ROLE_ADMIN"));
      boolean isOwner=user.getUsername().equals(authentication.getName());
      if (!isOwner && !isAdmin){
         throw new AccessDeniedException("You can only modify your account!");
      }
   }

   private User findByUuid(UUID uuid) {
      return userRepository.findByUuid(uuid)
              .orElseThrow(() -> new ResourceNotFoundException("User not found: " + uuid));
   }

   private User findByUsername(String username) {
      return userRepository.findByUsername(username)
              .orElseThrow(() -> new ResourceNotFoundException("User not found: " + username));
   }

   private UserResponse toResponse(User u) {
      return new UserResponse(u.getUuid(), u.getUsername(), u.getEmail(), u.getRole(), u.getCreatedAt());
   }
}