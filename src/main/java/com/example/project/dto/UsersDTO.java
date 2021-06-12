package com.example.project.dto;

import com.example.project.entity.Role;
import lombok.Data;

import java.util.Set;

@Data
public class UsersDTO {
    private Long id;

    private String username;

    private String password;

    private Set<Role> roles;
}
