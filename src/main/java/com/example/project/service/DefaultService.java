package com.example.project.service;

import com.example.project.entity.Product;
import com.example.project.entity.ProductType;
import com.example.project.repository.ProductRepository;
import com.example.project.repository.ProductTypeRepository;
import com.example.project.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DefaultService {
    @Autowired
    ProductTypeRepository productTypeRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    UsersRepository usersRepository;

    public String checkAuthorization(Principal principal) {
        if(principal == null) {
            return "noBody";
        }
        else {
            return principal.getName();
        }
    }

    public Map<ProductType, List<Product>> getMapProductType() {
        Iterable<ProductType> types = productTypeRepository.findAll();

        Map<ProductType, List<Product>> map = new HashMap<>();
        types.forEach(type -> map.put(type, productRepository.findByProductType(type)));

        return map;
    }

    public int getCountCookies(Principal principal, HttpServletRequest request) {
        if (principal == null)
            return 0;

        Cookie[] cookies = request.getCookies();

        int count = 0;
        for (Cookie cookie : cookies) {
            if (cookie.getName().contains(principal.getName()))
                count++;
        }
        return count;
    }

    public Product findProductById(Long id) {
        return productRepository.findById(id).orElse(null);
    }
}
