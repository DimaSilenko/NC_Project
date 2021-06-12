package com.example.project.repository;

import com.example.project.entity.Product;
import com.example.project.entity.ProductType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByProductType(ProductType productType);
    Optional<Product> findById(Long id);
}
