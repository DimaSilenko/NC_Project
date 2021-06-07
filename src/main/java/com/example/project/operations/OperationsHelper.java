package com.example.project.operations;

import com.example.project.entity.Product;
import com.example.project.entity.Users;
import com.example.project.repository.ProductRepository;
import com.example.project.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

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

    public static Users check(Long userId, Users user,
                              UsersRepository usersRepository, PasswordEncoder passwordEncoder) {

        Users temp = usersRepository.findById(userId).orElse(new Users());
        if (user.getUsername() != null
                && !user.getUsername().equals(temp.getUsername())
                && usersRepository.findByUsername(user.getUsername()) != null) {
            Users u = new Users();
            u.setUsername("Username is already taken");
            return u;
        }

        if (user.getUsername() == null)
            user.setUsername(temp.getUsername());
        if (!user.isActive())
            user.setActive(temp.isActive());
        if (user.getRoles() == null)
            user.setRoles(temp.getRoles());
        if (user.getPassword() == null)
            user.setPassword(temp.getPassword());
        else
            user.setPassword(passwordEncoder.encode(user.getPassword()));

        user.setId(userId);

        return user;
    }
}
