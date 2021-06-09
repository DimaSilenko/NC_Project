package com.example.project.controller;

import com.example.project.dto.ProductDTO;
import com.example.project.dto.ProductTypeDTO;
import com.example.project.entity.Product;
import com.example.project.entity.ProductType;
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

    // Add new film
    @GetMapping("/addFilm")
    public String addFilm(ProductDTO productDTO, ProductTypeDTO productTypeDTO) {
        return "addFilm";
    }

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

    @PostMapping("/delete")
    public String delete(ProductDTO product) {
        adminService.deleteProduct(product.getId());

        return "redirect:/";
    }
}
