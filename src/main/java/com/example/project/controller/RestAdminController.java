package com.example.project.controller;

import com.example.project.dto.ProductDTO;
import com.example.project.dto.UsersDTO;
import com.example.project.model.ProductCreationRequest;
import com.example.project.service.AdminService;
import com.example.project.service.dto.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rest")
@PreAuthorize("hasAuthority('ADMIN')")
public class RestAdminController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private UsersService usersService;


    @GetMapping("/restProducts")
    public List<ProductDTO> retrieveAllProducts() {
        return adminService.findAllProducts();
    }

    @PostMapping("/restProducts")
    public ProductDTO createProduct(@RequestBody ProductCreationRequest productCreationRequest) {
        return adminService.restCreateProduct(productCreationRequest);
    }

    @DeleteMapping("restProducts/{id}")
    public String deleteProduct(@PathVariable(value = "id") Long productId) {
        adminService.deleteProduct(productId);
        return "Deleted";
    }

    @GetMapping("/restUsers")
    public List<UsersDTO> retrieveAllUsers() {
        return usersService.findAll();
    }

    @PutMapping("/restUsers/{id}")
    public UsersDTO updateUser(@PathVariable(value = "id") Long userId, @RequestBody UsersDTO user) {
        return adminService.restUpdateUser(userId, user);
    }

    @PutMapping("/restActiveUser/{id}")
    public String changeActiveStatus(@PathVariable(value = "id") Long userId) {
        return usersService.changeActiveStatus(userId);
    }
}


