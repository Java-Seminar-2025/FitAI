package com.fitai.fitai.dto;

import java.time.LocalDateTime;

public record UserResponse(
        Integer id,
        String username,
        String email,
        String role,
        LocalDateTime createdAt) {
}