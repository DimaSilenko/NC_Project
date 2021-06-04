package com.example.project.controller;

import com.example.project.entity.Product;
import com.example.project.entity.ProductType;
import com.example.project.entity.Role;
import com.example.project.entity.Users;
import com.example.project.operations.ProductService;
import com.example.project.repository.ProductRepository;
import com.example.project.repository.ProductTypeRepository;
import com.example.project.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@PreAuthorize("hasAuthority('ADMIN')")
public class AdminController {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductTypeRepository productTypeRepository;

    @GetMapping("/userList")
    public String userList(Model model) {
        model.addAttribute("allUsers", usersRepository.findAll());
        return "userList";
    }


    // Add new film
    @GetMapping("/addFilm")
    public String addFilm(Product product, ProductType productType) {
        return "addFilm";
    }

    @PostMapping("/addFilm")
    public String addFilmDB(
            @Valid Product product,
            BindingResult bindingResult,
            ProductType productType,
            @RequestParam("file") MultipartFile file,
            RedirectAttributes attributes
    ) {
        return ProductService.addProduct(product, bindingResult, productType, file, attributes, productTypeRepository, productRepository);
    }

    @PostMapping("/delete")
    public String delete(Model model, Product product) {
        Optional<Product> productFromDB = productRepository.findById(product.getId());

        productRepository.delete(productFromDB.get());

        return "redirect:/";
    }
}
