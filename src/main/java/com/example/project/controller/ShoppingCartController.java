package com.example.project.controller;

import com.example.project.entity.Product;
import com.example.project.operations.OperationsHelper;
import com.example.project.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;

@Controller
public class ShoppingCartController {

    @Autowired
    private ProductRepository productRepository;

    @GetMapping("/shoppingCart")
    public String shoppingCart(Model model, HttpServletRequest request, Principal principal) {
        Cookie[] cookies = request.getCookies();
        model.addAttribute("products", OperationsHelper.getShoppingCart(cookies, principal, productRepository));
        return "shoppingCart";
    }

    @PostMapping("/buy")
    public String buyTicket(Product productBuy, HttpServletResponse response, Principal principal) {

        Cookie cookie = new Cookie(principal.getName()+"|"+ productBuy.getId().toString(),
                productBuy.getId().toString());
        cookie.setMaxAge(24*60*60);

        response.addCookie(cookie);

        return "redirect:/";
    }

    @GetMapping("/deleteTicket")
    public String deleteTicket(@RequestParam("id") Long id, HttpServletResponse response, Principal principal) {
        Cookie cookie = new Cookie(principal.getName()+"|"+ id.toString(), null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return "redirect:/shoppingCart";
    }
}
