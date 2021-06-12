package com.example.project.model;

import com.example.project.dto.ProductDTO;
import com.example.project.dto.ProductTypeDTO;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductCreationRequest {
    private ProductDTO product;
    private ProductTypeDTO productType;
}
