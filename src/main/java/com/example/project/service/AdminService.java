package com.example.project.service;

import com.example.project.entity.Product;
import com.example.project.entity.ProductType;
import com.example.project.entity.Users;
import com.example.project.model.ProductCreationRequest;
import com.example.project.repository.ProductRepository;
import com.example.project.repository.ProductTypeRepository;
import com.example.project.repository.UsersRepository;
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
import java.util.Optional;

@Service
public class AdminService {
    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductTypeRepository productTypeRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<Users> findAllUsers() {
        return usersRepository.findAll();
    }

    public List<Product> findAllProducts() {
        return productRepository.findAll();
    }

    public Product saveToRepo(Product product, ProductType productType) {
        ProductType temp = productTypeRepository.findByType(productType.getType());
        if (temp == null) {
            productTypeRepository.save(productType);
        } else {
            productType.setId(temp.getId());
        }
        product.setProductType(productType);

        return product;
    }

    public String addProduct(
            Product product,
            BindingResult bindingResult,
            ProductType productType,
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
            productRepository.save(product);
            return "redirect:/";
        }
    }

    public void deleteProduct(Long productId) {
        Optional<Product> productFromDB = productRepository.findById(productId);

        productRepository.delete(productFromDB.get());
    }

    public Product restCreateProduct(ProductCreationRequest productCreationRequest) {
        ProductType productType = productCreationRequest.getProductType();
        Product product = productCreationRequest.getProduct();

        return productRepository.save(saveToRepo(product, productType));
    }

    public Users restUpdateProduct(Long userId, Users user) {

        Users temp = usersRepository.findById(userId).orElse(new Users());
        if (user.getUsername() != null
                && !user.getUsername().equals(temp.getUsername())
                && usersRepository.findByUsername(user.getUsername()) != null) {
            user = new Users();
            user.setUsername("Username is already taken");
        }
        else {
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
