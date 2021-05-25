package com.example.project.controller;

import com.example.project.entity.Role;
import com.example.project.entity.Users;
import com.example.project.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Collections;
import java.util.Map;

@Controller
public class RegistrationController {

    @Autowired
    UsersRepository usersRepository;

    @GetMapping("/registration")
    public String registration(Model model) {
        model.addAttribute("usersModel", new Users());
        return "registration";
    }

    @PostMapping("/registration")
    public String registration(Model model, Users users) {
        Users userFromDB = usersRepository.findByUsername(users.getUsername());

        if (userFromDB != null) {
            model.addAttribute("message", "User exists");
            return "registration";
        }

        users.setActive(true);
        users.setRoles(Collections.singleton(Role.USER));
        usersRepository.save(users);

        return "login";
    }
}
