package com.example.project.controller;

import com.example.project.service.DefaultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@Controller
public class DefaultController {

    @Autowired
    private DefaultService defaultService;

    // Main page
    @GetMapping("/")
    public String index(Model model, Principal principal, HttpServletRequest request) {
        model.addAttribute("curUser", defaultService.checkAuthorization(principal));

        model.addAttribute("shoppingCart", defaultService.getCountCookies(principal, request));

        model.addAttribute("map", defaultService.getMapProductType());
        return "index";
    }

    // info page about film
    @GetMapping("/information")
    public String product(@RequestParam("id") Long id, Model model, Principal principal, HttpServletRequest request) {
        model.addAttribute("product", defaultService.findProductById(id));

        model.addAttribute("curUser", defaultService.checkAuthorization(principal));

        model.addAttribute("shoppingCart", defaultService.getCountCookies(principal, request));

        return "information";
    }
}
