package com.example.project.service;

import com.example.project.dto.ProductDTO;
import com.example.project.dto.ProductTypeDTO;
import com.example.project.repository.UsersRepository;
import com.example.project.service.dto.ProductService;
import com.example.project.service.dto.ProductTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DefaultService {
    @Autowired
    ProductTypeService productTypeService;

    @Autowired
    ProductService productService;

    @Autowired
    UsersRepository usersRepository;

    public String checkAuthorization(Principal principal) {
        if(principal == null) {
            return "noBody";
        }
        else {
            return principal.getName();
        }
    }

    public Map<ProductTypeDTO, List<ProductDTO>> getMapProductType() {
        Iterable<ProductTypeDTO> types = productTypeService.findAll();

        Map<ProductTypeDTO, List<ProductDTO>> map = new HashMap<>();
        types.forEach(type -> map.put(type, productService.findByProductType(type)));

        return map;
    }

    public int getCountCookies(Principal principal, HttpServletRequest request) {
        if (principal == null)
            return 0;

        Cookie[] cookies = request.getCookies();

        int count = 0;
        for (Cookie cookie : cookies) {
            if (cookie.getName().contains(principal.getName()))
                count++;
        }
        return count;
    }

    public ProductDTO findProductById(Long id) {
        return productService.findById(id);
    }
}
