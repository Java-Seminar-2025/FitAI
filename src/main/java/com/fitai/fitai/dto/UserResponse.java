package com.fitai.fitai.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record UserResponse(
        UUID uuid,
        String username,
        String email,
        String role,
        LocalDateTime createdAt) {
}