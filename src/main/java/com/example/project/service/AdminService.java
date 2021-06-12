package com.example.project.service;

import com.example.project.dto.ProductDTO;
import com.example.project.dto.ProductTypeDTO;
import com.example.project.dto.UsersDTO;
import com.example.project.entity.Users;
import com.example.project.model.ProductCreationRequest;
import com.example.project.repository.UsersRepository;
import com.example.project.service.dto.ProductService;
import com.example.project.service.dto.ProductTypeService;
import com.example.project.service.dto.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@Service
public class AdminService {
    @Autowired
    private UsersService usersService;

    @Autowired
    UsersRepository usersRepository;

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductTypeService productTypeService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<UsersDTO> findAllUsers() {
        return usersService.findAll();
    }

    public List<ProductDTO> findAllProducts() {
        return productService.findAll();
    }

    public ProductDTO saveToRepo(ProductDTO product, ProductTypeDTO productType) {
        ProductTypeDTO temp = productTypeService.findByType(productType.getType());
        if (temp == null) {
            productType = productTypeService.save(productType);
        } else {
            productType.setId(temp.getId());
        }
        product.setProductType(productTypeService.setProductType(productType));

        return product;
    }

    public String addProduct(
            ProductDTO product,
            BindingResult bindingResult,
            ProductTypeDTO productType,
            MultipartFile file,
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
                String UPLOAD_DIR = "./src/main/resources/static/images/";
                Path path = Paths.get(UPLOAD_DIR + fileName);
                Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                e.printStackTrace();
            }

            product = saveToRepo(product, productType);

            product.setImage(fileName);
            productService.save(product);
            return "redirect:/";
        }
    }

    public void deleteProduct(Long productId) {
        ProductDTO productFromDB = productService.findById(productId);

        productService.delete(productFromDB);
    }

    public ProductDTO restCreateProduct(ProductCreationRequest productCreationRequest) {
        ProductTypeDTO productType = productCreationRequest.getProductType();
        ProductDTO product = productCreationRequest.getProduct();

        return productService.save(saveToRepo(product, productType));
    }

    public Users restUpdateUser(Long userId, Users user) {

        Users temp = usersRepository.findById(userId).orElse(new Users());
        if (user.getUsername() != null
                && !user.getUsername().equals(temp.getUsername())
                && usersRepository.findByUsername(user.getUsername()) != null) {
            user = new Users();
            user.setUsername("Username is already taken");
        } else {
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
        }

        if (user.getId() == null)
            return user;
        return usersRepository.save(user);
    }
}
