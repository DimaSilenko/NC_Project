package com.example.project.controller;

import com.example.project.dto.ProductDTO;
import com.example.project.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;

@Controller
public class ShoppingCartController {

    @Autowired
    private ShoppingCartService shoppingCartService;

    @GetMapping("/shoppingCart")
    public String shoppingCart(Model model, HttpServletRequest request, Principal principal) {
        model.addAttribute("products", shoppingCartService.getShoppingCart(request, principal));
        return "shoppingCart";
    }

    @PostMapping("/buy")
    public String buyTicket(ProductDTO productBuy, HttpServletResponse response, Principal principal) {

        response.addCookie(shoppingCartService.addToShoppingCart(productBuy, principal));

        return "redirect:/";
    }

    @GetMapping("/deleteTicket")
    public String deleteTicket(@RequestParam("id") Long id, HttpServletResponse response, Principal principal) {

        response.addCookie(shoppingCartService.deleteFromShoppingCart(id, principal));

        return "redirect:/shoppingCart";
    }
}
