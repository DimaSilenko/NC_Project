package com.example.project.controller;

import com.example.project.dto.ProductDTO;
import com.example.project.dto.ProductTypeDTO;
import com.example.project.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@PreAuthorize("hasAuthority('ADMIN')")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @GetMapping("/userList")
    public String userList(Model model) {
        model.addAttribute("allUsers", adminService.findAllUsers());
        return "userList";
    }

    // Get addFilm page
    @GetMapping("/addFilm")
    public String addFilm(Model model) {
        model.addAttribute("productDTO", new ProductDTO());
        model.addAttribute("productTypeDTO", new ProductTypeDTO());
        return "addFilm";
    }

    // Post to add new film
    @PostMapping("/addFilm")
    public String addFilmDB(
            @Valid ProductDTO product,
            BindingResult bindingResult,
            ProductTypeDTO productType,
            @RequestParam("file") MultipartFile file,
            RedirectAttributes attributes
    ) {
        return adminService.addProduct(product, bindingResult, productType, file, attributes);
    }

    // Delete film
    @PostMapping("/delete")
    public String delete(ProductDTO product) {
        adminService.deleteProduct(product.getId());

        return "redirect:/";
    }

    @GetMapping("/errorPage")
    public String error() {
        return "errorPage";
    }
}
