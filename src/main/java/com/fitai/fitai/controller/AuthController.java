package com.fitai.fitai.controller;

import com.fitai.fitai.dto.LoginRequest;
import com.fitai.fitai.dto.RegisterRequest;
import com.fitai.fitai.dto.UserResponse;
import com.fitai.fitai.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

   private final UserService userService;
   private final AuthenticationManager authenticationManager;
   private final SecurityContextRepository securityContextRepository = new HttpSessionSecurityContextRepository();

   public AuthController(UserService userService, AuthenticationManager authenticationManager) {
      this.userService = userService;
      this.authenticationManager = authenticationManager;
   }

   @PostMapping("/register")
   public ResponseEntity<UserResponse> register(@Valid @RequestBody RegisterRequest request) {
      return ResponseEntity.status(HttpStatus.CREATED).body(userService.register(request));
   }

   @PostMapping("/login")
   public UserResponse login(@Valid @RequestBody LoginRequest request,
                             HttpServletRequest httpRequest,
                             HttpServletResponse httpResponse) {
      Authentication authentication = authenticationManager.authenticate(
              new UsernamePasswordAuthenticationToken(request.username(), request.password()));

      SecurityContext context = SecurityContextHolder.createEmptyContext();
      context.setAuthentication(authentication);
      SecurityContextHolder.setContext(context);
      securityContextRepository.saveContext(context, httpRequest, httpResponse);

      return userService.getByUsername(authentication.getName());
   }
}