package com.example.project.service.dto;

import com.example.project.dto.UsersDTO;
import com.example.project.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsersService {
    @Autowired
    UsersRepository usersRepository;

    @Autowired
    MappingUtils mappingUtils;

    public UsersDTO findByUsername(String username) {
        return mappingUtils.mapToUsersDTO(usersRepository.findByUsername(username));
    }

    public List<UsersDTO> findAll() {
        return usersRepository.findAll().stream()
                .map(mappingUtils::mapToUsersDTO)
                .collect(Collectors.toList());
    }
}
