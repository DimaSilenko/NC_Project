package com.example.project.controller;

import com.example.project.entity.Role;
import com.example.project.entity.Users;
import com.example.project.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Collections;

@Controller
public class RegistrationController {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    UsersRepository usersRepository;

    @GetMapping("/registration")
    public String registration(Model model) {
        model.addAttribute("usersModel", new Users());
        return "registration";
    }

    @PostMapping("/registration")
    public String registration(Model model, Users users, RedirectAttributes attributes) {
        Users userFromDB = usersRepository.findByUsername(users.getUsername());

        if (userFromDB != null) {
            attributes.addFlashAttribute("message", "User already exist!");
            model.addAttribute("userModel", new Users());
            return "redirect:/registration";
        }

        users.setActive(true);
        users.setRoles(Collections.singleton(Role.USER));
        users.setPassword(passwordEncoder.encode(users.getPassword()));
        usersRepository.save(users);

        return "login";
    }
}
