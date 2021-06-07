package com.example.project.service;

import com.example.project.entity.Product;
import com.example.project.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Service
public class ShoppingCartService {
    @Autowired
    private ProductRepository productRepository;

    public List<Product> getShoppingCart(HttpServletRequest request, Principal principal) {
        Cookie[] cookies = request.getCookies();

        List<Product> products = new ArrayList<>();
        for (Cookie cookie : cookies) {
            if (cookie.getName().contains(principal.getName())) {
                Product product = productRepository.findById(Long.parseLong(cookie.getValue())).orElse(null);
                products.add(product);
            }
        }
        return products;
    }

    public Cookie addToShoppingCart(Product productBuy, Principal principal) {
        Cookie cookie = new Cookie(principal.getName()+"|"+ productBuy.getId().toString(),
                productBuy.getId().toString());

        cookie.setMaxAge(24*60*60);

        return cookie;
    }

    public Cookie deleteFromShoppingCart(Long id, Principal principal) {
        Cookie cookie = new Cookie(principal.getName()+"|"+ id.toString(), null);

        cookie.setMaxAge(0);

        return cookie;
    }
}
