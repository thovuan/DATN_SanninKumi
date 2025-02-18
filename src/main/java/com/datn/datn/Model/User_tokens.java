package com.datn.datn.Model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;

@Entity
@Setter
@Getter
@Table(name = "user_tokens")
public class User_tokens {

    @Id
    @UuidGenerator
    @Column(name = "id")
    private String id;


    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    @ManyToOne(fetch = FetchType.EAGER)
    private Users user; // Assuming you have a User entity

    @Column(nullable = false, length = 2000)
    private String token;

    @Column(nullable = true, name = "created_at")
    private LocalDateTime issuedAt;

    @Column(nullable = false, name = "expiresAt")
    private LocalDateTime expiresAt;

    @Column(nullable = false, name = "is_active")
    private boolean isActive = true; // Useful if you want to invalidate tokens
}
