package com.example.project.controller;

import com.example.project.entity.Product;
import com.example.project.entity.ProductType;
import com.example.project.entity.Role;
import com.example.project.entity.Users;
import com.example.project.repository.ProductRepository;
import com.example.project.repository.ProductTypeRepository;
import com.example.project.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@PreAuthorize("hasAuthority('ADMIN')")
public class AdminController {

    private final String UPLOAD_DIR = "./src/main/resources/static/images/";

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductTypeRepository productTypeRepository;

    @GetMapping("/userList")
    public String userList(Model model) {
        model.addAttribute("allUsers", usersRepository.findAll());
        /*Iterable<Users> usersIt = usersRepository.findAll();
        model.addAttribute("usersIt", usersIt);
        model.addAttribute("usersModel", new Users());*/
        return "userList";
    }

    @GetMapping("/userEdit")
    public String userEdit(@RequestParam("id") Long id, Model model) {
        Users user = usersRepository.findById(id).orElse(null);
        model.addAttribute("user", user);
        model.addAttribute("roles", Role.values());
        return "userEdit";
    }

    @PostMapping("/userList")
    public String userSave(
            @RequestParam String username,
            @RequestParam Map<String, String> form,
            Users user
    ) {
        Users userUpdate = usersRepository.findById(user.getId()).orElse(null);
        userUpdate.setUsername(username);

        Set<String> roles = Arrays.stream(Role.values()).map(Role::name).collect(Collectors.toSet());

        userUpdate.getRoles().clear();

        for (String key : form.keySet()) {
            if (roles.contains(key)) {
                userUpdate.getRoles().add(Role.valueOf(key));
            }
        }

        //usersRepository.save(userUpdate);
        return "redirect:/userList";
    }


    //Здесь более полезная часть про добавление и удаление
    // Add new film
    @GetMapping("/addFilm")
    public String addFilm(Product product, ProductType productType) {
        return "addFilm";
    }

    @PostMapping("/addFilm")
    public String addFilmDB(
            @Valid Product product,
            BindingResult bindingResult,
            ProductType productType,
            @RequestParam("file") MultipartFile file,
            RedirectAttributes attributes
    ) {
        // check validation errors
        if (bindingResult.hasErrors()) {
            return "/addFilm";
        } else {

            // check if file is empty
            if (file.isEmpty()) {
                attributes.addFlashAttribute("message", "Please select a file to upload.");
                return "redirect:/addFilm";
            }

            // normalize the file path
            String fileName = StringUtils.cleanPath(file.getOriginalFilename());

            // save the file on the local file system
            try {
                Path path = Paths.get(UPLOAD_DIR + fileName);
                Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                e.printStackTrace();
            }


            ProductType temp = productTypeRepository.findByType(productType.getType());
            if (temp == null) {
                productTypeRepository.save(productType);
            } else {
                productType.setId(temp.getId());
            }
            product.setProductType(productType);
            product.setImage(fileName);
            productRepository.save(product);
            return "redirect:/";
        }
    }

    @PostMapping("/delete")
    public String delete(Model model, Product product) {
        Optional<Product> productFromDB = productRepository.findById(product.getId());

        productRepository.delete(productFromDB.get());

        return "redirect:/";
    }
}
