package com.fitai.fitai.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

   @ExceptionHandler(ResourceNotFoundException.class)
   public ResponseEntity<Map<String, Object>> handleNotFound(ResourceNotFoundException ex) {
      return error(HttpStatus.NOT_FOUND, ex.getMessage());
   }

   @ExceptionHandler(DuplicateResourceException.class)
   public ResponseEntity<Map<String, Object>> handleDuplicate(DuplicateResourceException ex) {
      return error(HttpStatus.CONFLICT, ex.getMessage());
   }

   @ExceptionHandler(BadCredentialsException.class)
   public ResponseEntity<Map<String, Object>> handleBadCredentials(BadCredentialsException ex) {
      return error(HttpStatus.UNAUTHORIZED, "Invalid username or password");
   }

   @ExceptionHandler(DataIntegrityViolationException.class)
   public ResponseEntity<Map<String, Object>> handleIntegrity(DataIntegrityViolationException ex) {
      return error(HttpStatus.CONFLICT, "Operation violates data integrity (e.g. record is still referenced)");
   }

   @ExceptionHandler(MethodArgumentNotValidException.class)
   public ResponseEntity<Map<String, Object>> handleValidation(MethodArgumentNotValidException ex) {
      Map<String, String> fields = new LinkedHashMap<>();
      ex.getBindingResult().getFieldErrors()
              .forEach(fe -> fields.put(fe.getField(), fe.getDefaultMessage()));
      Map<String, Object> body = new LinkedHashMap<>();
      body.put("status", 400);
      body.put("error", "Validation failed");
      body.put("fields", fields);
      return ResponseEntity.badRequest().body(body);
   }

   private ResponseEntity<Map<String, Object>> error(HttpStatus status, String message) {
      Map<String, Object> body = new LinkedHashMap<>();
      body.put("status", status.value());
      body.put("error", message);
      return ResponseEntity.status(status).body(body);
   }
}