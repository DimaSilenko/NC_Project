package com.example.project.model;

import com.example.project.dto.ProductDTO;
import com.example.project.dto.ProductTypeDTO;

public class ProductCreationRequest {
    private ProductDTO product;
    private ProductTypeDTO productType;

    public ProductDTO getProduct() {
        return product;
    }

    public void setProduct(ProductDTO product) {
        this.product = product;
    }

    public ProductTypeDTO getProductType() {
        return productType;
    }

    public void setProductType(ProductTypeDTO productType) {
        this.productType = productType;
    }

    public ProductCreationRequest(ProductDTO product, ProductTypeDTO productType) {
        this.product = product;
        this.productType = productType;
    }
}
