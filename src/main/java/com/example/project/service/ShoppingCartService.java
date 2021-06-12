package com.example.project.service;

import com.example.project.dto.ProductDTO;
import com.example.project.service.dto.ProductService;
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
    private ProductService productService;

    public List<ProductDTO> getShoppingCart(HttpServletRequest request, Principal principal) {
        Cookie[] cookies = request.getCookies();

        List<ProductDTO> products = new ArrayList<>();
        for (Cookie cookie : cookies) {
            if (cookie.getName().contains(principal.getName())) {
                ProductDTO product = productService.findById(Long.parseLong(cookie.getValue()));
                products.add(product);
            }
        }
        return products;
    }

    public Cookie addToShoppingCart(ProductDTO productBuy, Principal principal) {
        Cookie cookie = new Cookie(principal.getName() + "|" + productBuy.getId().toString(),
                productBuy.getId().toString());

        cookie.setMaxAge(24 * 60 * 60);

        return cookie;
    }

    public Cookie deleteFromShoppingCart(Long id, Principal principal) {
        Cookie cookie = new Cookie(principal.getName() + "|" + id.toString(), null);

        cookie.setMaxAge(0);

        return cookie;
    }
}
