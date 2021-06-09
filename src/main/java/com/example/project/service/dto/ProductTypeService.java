package com.example.project.service.dto;

import com.example.project.dto.ProductTypeDTO;
import com.example.project.entity.ProductType;
import com.example.project.repository.ProductTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductTypeService {
    @Autowired
    ProductTypeRepository productTypeRepository;

    @Autowired
    MappingUtils mappingUtils;

    public List<ProductTypeDTO> findAll() {
        return productTypeRepository.findAll().stream()
                .map(mappingUtils::mapToProductTypeDTO)
                .collect(Collectors.toList());
    }

    public ProductTypeDTO findByType(String type) {
        return mappingUtils.mapToProductTypeDTO(productTypeRepository.findByType(type));
    }

    public ProductTypeDTO save(ProductTypeDTO dto) {
        return mappingUtils.mapToProductTypeDTO(productTypeRepository.save(mappingUtils.mapToProductType(dto)));
    }

    public ProductType setProductType(ProductTypeDTO dto) {
        return mappingUtils.mapToProductType(dto);
    }
}
