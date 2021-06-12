package com.example.project.service.dto;

import com.example.project.dto.UsersDTO;
import com.example.project.entity.Users;
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

    public UsersDTO findById(Long id) {
        return mappingUtils.mapToUsersDTO(
                usersRepository.findById(id)
                        .orElse(new Users())
        );
    }

    public UsersDTO save(UsersDTO dto) {
        Users u = mappingUtils.mapToUsers(dto);
        u.setActive(true);
        return mappingUtils.mapToUsersDTO(usersRepository.save(u));
    }

    public String changeActiveStatus(Long userId) {
        Users u = usersRepository.findById(userId).orElse(new Users());
        if (u.isActive()) {
            u.setActive(false);
            usersRepository.save(u);
            return "Deactivate";
        } else {
            u.setActive(true);
            usersRepository.save(u);
            return "Activate";
        }
    }
}
