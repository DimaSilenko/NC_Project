package com.example.project.controller;

import com.example.project.entity.Users;
import com.example.project.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class RegistrationController {

    @Autowired
    private RegistrationService registrationService;

    // get registration page
    @GetMapping("/registration")
    public String registration(Model model) {
        model.addAttribute("usersModel", new Users());
        return "registration";
    }

    // post registration info and return login page
    @PostMapping("/registration")
    public String registration(Model model, Users users, RedirectAttributes attributes) {
        if (!registrationService.addNewUser(users)) {
            attributes.addFlashAttribute("message", "User already exist!");
            model.addAttribute("userModel", new Users());
            return "redirect:/registration";
        }

        return "login";
    }
}
