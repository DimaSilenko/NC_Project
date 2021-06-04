package com.example.project.operations;

import com.example.project.entity.Product;
import com.example.project.entity.Users;
import com.example.project.repository.ProductRepository;
import com.example.project.repository.UsersRepository;

import javax.servlet.http.Cookie;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

public class OperationsHelper {

    public static int culcCount(Cookie[] cookies, Principal principal) {
        int count = 0;
        for (Cookie cookie : cookies) {
            if (cookie.getName().contains(principal.getName()))
                count++;
        }
        return count;
    }

    public static List<Product>  getShoppingCart (
            Cookie[] cookies,
            Principal principal,
            ProductRepository productRepository
    ) {
        List<Product> products = new ArrayList<>();
        for (Cookie cookie : cookies) {
            if (cookie.getName().contains(principal.getName())) {
                Product product = productRepository.findById(Long.parseLong(cookie.getValue())).orElse(null);
                products.add(product);
            }
        }
        return products;
    }

    public static Users check(Long userId, Users user, UsersRepository usersRepository) {
        Users temp = usersRepository.findById(userId).orElse(null);

        if (user.getUsername() == null)
            user.setUsername(temp.getUsername());
        if (user.getPassword() == null)
            user.setPassword(temp.getPassword());
        if (!user.isActive())
            user.setActive(temp.isActive());
        if (user.getRoles() == null)
            user.setRoles(temp.getRoles());

        user.setId(userId);

        return user;
    }
}
