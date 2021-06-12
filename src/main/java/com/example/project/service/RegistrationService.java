package com.example.project.service;

import com.example.project.dto.UsersDTO;
import com.example.project.entity.Role;
import com.example.project.service.dto.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class RegistrationService {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    UsersService usersService;

    public boolean addNewUser(UsersDTO users) {
        UsersDTO userFromDB = usersService.findByUsername(users.getUsername());

        if (userFromDB != null) {
            return false;
        }

        users.setRoles(Collections.singleton(Role.USER));
        users.setPassword(passwordEncoder.encode(users.getPassword()));
        usersService.save(users);

        return true;
    }
}
