package com.example.project.model;

import com.example.project.entity.Product;
import com.example.project.entity.ProductType;

public class ProductCreationRequest {
    private Product product;
    private ProductType productType;

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public ProductType getProductType() {
        return productType;
    }

    public void setProductType(ProductType productType) {
        this.productType = productType;
    }

    public ProductCreationRequest(Product product, ProductType productType) {
        this.product = product;
        this.productType = productType;
    }
}
