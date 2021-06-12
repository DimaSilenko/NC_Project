package com.example.project.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "users", uniqueConstraints = @UniqueConstraint(columnNames = {"username"}))
@Data
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // User's username
    private String username;

    // User's password
    private String password;

    // Status of user's profile
    private boolean active;

    // User's role or roles
    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
    //храним отдельную таблицу с ролями
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;
}
