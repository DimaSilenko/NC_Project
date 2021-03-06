package com.example.project.service.dto;

import com.example.project.dto.ProductDTO;
import com.example.project.dto.ProductTypeDTO;
import com.example.project.dto.UsersDTO;
import com.example.project.entity.Product;
import com.example.project.entity.ProductType;
import com.example.project.entity.Users;
import org.springframework.stereotype.Service;

@Service
public class MappingUtils {
    // From entity to dto
    public ProductDTO mapToProductDTO(Product product) {
        ProductDTO dto = new ProductDTO();

        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setPrice(product.getPrice());
        dto.setImage(product.getImage());
        dto.setDescription(product.getDescription());
        dto.setYear(product.getYear());
        dto.setPg(product.getPg());
        dto.setDirector(product.getDirector());
        dto.setProductType(product.getProductType());

        return dto;
    }

    // From dto to entity
    public Product mapToProduct(ProductDTO dto) {
        Product product = new Product();

        product.setId(dto.getId());
        product.setName(dto.getName());
        product.setPrice(dto.getPrice());
        product.setImage(dto.getImage());
        product.setDescription(dto.getDescription());
        product.setYear(dto.getYear());
        product.setPg(dto.getPg());
        product.setDirector(dto.getDirector());
        product.setProductType(dto.getProductType());

        return product;
    }

    // From entity to dto
    public ProductTypeDTO mapToProductTypeDTO(ProductType productType) {
        if (productType == null)
            return null;

        ProductTypeDTO dto = new ProductTypeDTO();

        dto.setId(productType.getId());
        dto.setType(productType.getType());

        return dto;
    }

    // From dto to entity
    public ProductType mapToProductType(ProductTypeDTO dto) {
        ProductType productType = new ProductType();

        productType.setId(dto.getId());
        productType.setType(dto.getType());

        return productType;
    }

    // From entity to dto
    public UsersDTO mapToUsersDTO(Users users) {
        if (users == null)
            return null;

        UsersDTO dto = new UsersDTO();

        dto.setId(users.getId());
        dto.setUsername(users.getUsername());
        dto.setPassword(users.getPassword());
        dto.setRoles(users.getRoles());

        return dto;
    }

    // From dto to entity
    public Users mapToUsers(UsersDTO dto) {
        Users users = new Users();

        users.setId(dto.getId());
        users.setUsername(dto.getUsername());
        users.setPassword(dto.getPassword());
        users.setRoles(dto.getRoles());

        return users;
    }
}
