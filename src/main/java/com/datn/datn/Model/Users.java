package com.datn.datn.Model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;
import jakarta.validation.constraints.Email;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UuidGenerator;

@Entity
@Getter
@Setter
@DynamicInsert
@DynamicUpdate
@Table(name = "users", uniqueConstraints = {@UniqueConstraint(columnNames = {"provider", "provider_id"})})
public class Users {

    @Id
    @UuidGenerator
    @Column(name = "id")
    private String user_id;

    @Column(name = "email", unique = true)
    @Email(message = "Invalid Email")
    private String email;

    @Column(name = "name")
    private String name;

    @Column(name = "created_at")
    private LocalDateTime create_at;

    @Column(name = "updated_at")
    private LocalDateTime update_at;

    @Column(name = "username")
    private String username;

    @Column(name = "password_hash")
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "provider")
    private Provider provider = Provider.LOCAL;

    @Column(name = "provider_id")
    private String provider_id;

    @Column(name = "avatar_url")
    private String avatar_url;

    public enum Provider {
        LOCAL, GOOGLE, FACEBOOK, APPLE
    }

//    private String
}
