package com.fitai.fitai.controller;

import com.fitai.fitai.dto.UpdateUserRequest;
import com.fitai.fitai.dto.UserResponse;
import com.fitai.fitai.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

   private final UserService userService;

   public UserController(UserService userService) {
      this.userService = userService;
   }


   @GetMapping("/me")
   public UserResponse me(Principal principal) {
      return userService.getByUsername(principal.getName());
   }

   @PutMapping("/me")
   public UserResponse updateMe(Principal principal, @Valid @RequestBody UpdateUserRequest request) {
      return userService.updateByUsername(principal.getName(), request);
   }

   @DeleteMapping("/me")
   public ResponseEntity<Void> deleteMe(Principal principal, HttpServletRequest request) {
      userService.deleteByUsername(principal.getName());
      HttpSession session = request.getSession(false);
      if (session != null) {
         session.invalidate();
      }
      SecurityContextHolder.clearContext();
      return ResponseEntity.noContent().build();
   }

   // admin only
   @GetMapping
   public List<UserResponse> getAll() {
      return userService.getAll();
   }

   @GetMapping("/{id}")
   public UserResponse getById(@PathVariable Integer id) {
      return userService.getById(id);
   }

   @DeleteMapping("/{id}")
   public ResponseEntity<Void> delete(@PathVariable Integer id) {
      userService.deleteById(id);
      return ResponseEntity.noContent().build();
   }
}