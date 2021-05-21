package com.example.project.controller;

import com.example.project.entity.Users;
import com.example.project.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UsersController {

    @Autowired
    UsersRepository usersRepository;

    @GetMapping("/")
    public String index(Model model) {
        Iterable<Users> usersIt = usersRepository.findAll();
        model.addAttribute("usersIt", usersIt);
        model.addAttribute("usersModel", new Users());
        return "index";
    }

    @PostMapping("/")
    public String index(Model model, Users users) {
        usersRepository.save(users);
        Iterable<Users> usersIt = usersRepository.findAll();
        model.addAttribute("usersIt", usersIt);
        return "result";
        //return index(model);
    }
}
