package com.hdev.ecommerce.service;

import com.hdev.ecommerce.dto.UserDTO;
import com.hdev.ecommerce.dto.UserRequestDTO;
import com.hdev.ecommerce.entity.User;
import com.hdev.ecommerce.entity.UserRole;
import com.hdev.ecommerce.mapper.UserMapper;
import com.hdev.ecommerce.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class UserService {
    private static String NOT_FOUND = "Resource Not Found";
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDTO getUserById(Long id){
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(NOT_FOUND));
        return UserMapper.toUserDTO(user);
    }

    public List<UserDTO> getAllUsers(){
        List<User> users = userRepository.findAll();
        return users.stream().map(user -> UserMapper.toUserDTO(user)).toList();
    }

    public UserDTO createUser(UserRequestDTO dto){
        User user = UserMapper.toEntity(dto, new User());
        user.setRole(UserRole.CUSTOMER);
        user.setCreatedAt(Instant.now());
        userRepository.save(user);
        return UserMapper.toUserDTO(user);
    }

    public UserDTO updateUser(Long id, UserRequestDTO dto){
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(NOT_FOUND));
        user = UserMapper.toEntity(dto, user);
        user.setUpdatedAt(Instant.now());
        userRepository.save(user);
        return UserMapper.toUserDTO(user);
    }
}
