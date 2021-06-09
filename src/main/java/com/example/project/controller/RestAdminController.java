package com.example.project.controller;

import com.example.project.dto.ProductDTO;
import com.example.project.entity.Users;
import com.example.project.model.ProductCreationRequest;
import com.example.project.repository.UsersRepository;
import com.example.project.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping ("/rest")
@PreAuthorize("hasAuthority('ADMIN')")
public class RestAdminController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private UsersRepository usersRepository;


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
    public List<Users> retrieveAllUsers() {
        return usersRepository.findAll();
    }

    @PutMapping("/restUsers/{id}")
    public Users updateUser(@PathVariable(value = "id") Long userId, @RequestBody Users user) {
        return adminService.restUpdateProduct(userId, user);
    }
}


