package com.example.project.service.dto;

import com.example.project.dto.ProductDTO;
import com.example.project.dto.ProductTypeDTO;
import com.example.project.entity.Product;
import com.example.project.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private MappingUtils mappingUtils;

    public List<ProductDTO> findAll() {
        return productRepository.findAll().stream()
                .map(mappingUtils::mapToProductDTO)
                .collect(Collectors.toList());
    }

    public ProductDTO findById(Long id) {
        return mappingUtils.mapToProductDTO(
                productRepository.findById(id)
                        .orElse(new Product())
        );
    }

    public ProductDTO save(ProductDTO dto) {
        return mappingUtils.mapToProductDTO(productRepository.save(mappingUtils.mapToProduct(dto)));
    }

    public void delete(ProductDTO dto) {
        productRepository.delete(mappingUtils.mapToProduct(dto));
    }

    public List<ProductDTO> findByProductType(ProductTypeDTO type) {
        List<Product> list = productRepository.findByProductType(mappingUtils.mapToProductType(type));
        List<ProductDTO> dto = new ArrayList<>();
        for (Product product : list)
            dto.add(mappingUtils.mapToProductDTO(product));
        return dto;
    }
}
