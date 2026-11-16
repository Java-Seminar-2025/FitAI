package com.fitai.fitai.exception;

public class ResourceNotFoundException extends RuntimeException {
   public ResourceNotFoundException(String message) {
      super(message);
   }
}