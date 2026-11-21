package com.fitai.fitai.controller;

import com.fitai.fitai.dto.UpdateUserRequest;
import com.fitai.fitai.dto.UserResponse;
import com.fitai.fitai.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

@Validated
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
      invalidateSession(request);
      return ResponseEntity.noContent().build();
   }

   // admin only
   @GetMapping
   public List<UserResponse> getAll() {
      return userService.getAll();
   }

   @GetMapping("/{uuid}")
   public UserResponse getByUuid(@PathVariable UUID uuid) {
      return userService.getByUuid(uuid);
   }

   @PutMapping("/{uuid}")
   public UserResponse update(@PathVariable UUID uuid,
                              @Valid @RequestBody UpdateUserRequest request,
                              Authentication authentication) {
      return userService.updateByUuid(uuid, request, authentication);
   }

   @DeleteMapping("/{uuid}")
   public ResponseEntity<Void> delete(@PathVariable UUID uuid,
                                      Authentication authentication,
                                      HttpServletRequest request) {
      String currentUsername=authentication.getName();
      boolean isSelfDelete=userService.getByUuid(uuid).username().equals(currentUsername);
      userService.deleteByUuid(uuid,authentication);
      if (isSelfDelete) {
         invalidateSession(request);
      }
      return ResponseEntity.noContent().build();
   }

   private void invalidateSession(HttpServletRequest request) {
      HttpSession session = request.getSession(false);
      if (session != null) {
         session.invalidate();
      }
      SecurityContextHolder.clearContext();
   }
}