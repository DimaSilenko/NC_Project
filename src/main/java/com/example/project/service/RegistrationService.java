package com.example.project.service;

import com.example.project.entity.Role;
import com.example.project.entity.Users;
import com.example.project.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class RegistrationService {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    UsersRepository usersRepository;

    public boolean addNewUser(Users users) {
        Users userFromDB = usersRepository.findByUsername(users.getUsername());

        if (userFromDB != null) {
            return false;
        }

        users.setActive(true);
        users.setRoles(Collections.singleton(Role.USER));
        users.setPassword(passwordEncoder.encode(users.getPassword()));
        usersRepository.save(users);

        return true;
    }
}
