package com.example.project.controller;

import com.example.project.entity.Product;
import com.example.project.entity.Role;
import com.example.project.entity.Users;
import com.example.project.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
public class AdminController {

    @Autowired
    private UsersRepository usersRepository;

    @GetMapping("/userList")
    public String userList(Model model) {
        model.addAttribute("allUsers", usersRepository.findAll());
        /*Iterable<Users> usersIt = usersRepository.findAll();
        model.addAttribute("usersIt", usersIt);
        model.addAttribute("usersModel", new Users());*/
        return "userList";
    }

    @GetMapping("/userEdit")
    public String userEdit(@RequestParam("id") Long id, Model model) {
        Users user = usersRepository.findById(id).orElse(null);
        model.addAttribute("user", user);
        model.addAttribute("roles", Role.values());
        return "userEdit";
    }

    @PostMapping("/userList")
    public String userSave(
            @RequestParam String username,
            @RequestParam Map<String, String> form,
            Users user
    ) {
        Users userUpdate = usersRepository.findById(user.getId()).orElse(null);
        userUpdate.setUsername(username);

        Set<String> roles = Arrays.stream(Role.values()).map(Role::name).collect(Collectors.toSet());

        userUpdate.getRoles().clear();

        for (String key : form.keySet()) {
            if (roles.contains(key)) {
                userUpdate.getRoles().add(Role.valueOf(key));
            }
        }

        //usersRepository.save(userUpdate);
        return "redirect:/userList";
    }
}
