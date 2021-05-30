package com.example.project.controller;

import com.example.project.entity.Product;
import com.example.project.entity.ProductType;
import com.example.project.entity.Users;
import com.example.project.repository.ProductRepository;
import com.example.project.repository.ProductTypeRepository;
import com.example.project.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class DefaultController {

    @Autowired
    ProductTypeRepository productTypeRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    UsersRepository usersRepository;

    @GetMapping("/")
    public String index(Model model, Principal principal) {
        if(principal == null) {
            model.addAttribute("curUser", "noBody");
        }
        else {
            model.addAttribute("curUser", principal.getName());
        }

        Iterable<ProductType> types = productTypeRepository.findAll();

        Map<ProductType, List<Product>> map = new HashMap<>();
        types.forEach(type -> map.put(type, productRepository.findByProductType(type)));
        model.addAttribute("map", map);
        return "index";
    }

    @GetMapping("/information")
    public String product(@RequestParam("id") Long id, Model model, Principal principal) {
        Product product = productRepository.findById(id).orElse(null);
        model.addAttribute("product", product);
        if(principal == null) {
            model.addAttribute("curUser", "noBody");
        }
        else {
            model.addAttribute("curUser", principal.getName());
        }
        return "information";
    }
}
