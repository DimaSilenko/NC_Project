package com.example.project.operations;

import com.example.project.entity.Product;
import com.example.project.entity.ProductType;
import com.example.project.repository.ProductRepository;
import com.example.project.repository.ProductTypeRepository;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class ProductService {

    private static final String UPLOAD_DIR = "./src/main/resources/static/images/";

    public static Product saveToRepo(Product product, ProductType productType, ProductTypeRepository productTypeRepository) {
        ProductType temp = productTypeRepository.findByType(productType.getType());
        if (temp == null) {
            productTypeRepository.save(productType);
        } else {
            productType.setId(temp.getId());
        }
        product.setProductType(productType);

        return product;
    }

    public static String addProduct(
            Product product,
            BindingResult bindingResult,
            ProductType productType,
            MultipartFile file,
            RedirectAttributes attributes,
            ProductTypeRepository productTypeRepository,
            ProductRepository productRepository
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

            product = saveToRepo(product, productType, productTypeRepository);

            product.setImage(fileName);
            productRepository.save(product);
            return "redirect:/";
        }
    }
}
