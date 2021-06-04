package com.example.project.controller;

import com.example.project.entity.Product;
import com.example.project.entity.ProductType;
import com.example.project.entity.Users;
import com.example.project.model.ProductCreationRequest;
import com.example.project.operations.OperationsHelper;
import com.example.project.repository.ProductRepository;
import com.example.project.repository.ProductTypeRepository;
import com.example.project.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping ("/rest")
public class RestAdminController {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductTypeRepository productTypeRepository;

    @Autowired
    private UsersRepository usersRepository;

    @GetMapping("/restProducts")
    public List<Product> retrieveAllProducts() {
        return productRepository.findAll();
    }

    @PostMapping("/restProducts")
    public Product createProduct(@RequestBody ProductCreationRequest productCreationRequest) {
        ProductType productType = productCreationRequest.getProductType();
        Product product = productCreationRequest.getProduct();

        ProductType temp = productTypeRepository.findByType(productType.getType());
        if (temp == null) {
            productTypeRepository.save(productType);
        } else {
            productType.setId(temp.getId());
        }
        product.setProductType(temp);

        return productRepository.save(product);
    }

    @DeleteMapping("restProducts/{id}")
    public String deleteProduct(@PathVariable(value = "id") Long productId) {
        Product product = productRepository.findById(productId).orElse(null);

        productRepository.delete(product);
        return "Deleted";
    }

    @GetMapping("/restUsers")
    public List<Users> retrieveAllUsers() {
        return usersRepository.findAll();
    }

    @PutMapping("/restUsers/{id}")
    public Users updateUser(@PathVariable(value = "id") Long userId, @RequestBody Users user) {
        return usersRepository.save(OperationsHelper.check(userId, user, usersRepository));
    }
}


