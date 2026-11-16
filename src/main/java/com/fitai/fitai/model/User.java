package com.fitai.fitai.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Getter
@Setter 
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(unique=true, nullable = false)
    private String username;
    @Column(unique=true,nullable = false)
    private String email;
    @Column(nullable = false)
    private String password;

    // can be "USER" or "ADMIN"
   @Column(nullable = false)
   private String role = "USER";

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();
}
