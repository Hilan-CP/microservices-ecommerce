package com.hdev.user_service.service;

import com.hdev.user_service.dto.UserDTO;
import com.hdev.user_service.dto.UserRequestDTO;
import com.hdev.user_service.entity.User;
import com.hdev.user_service.entity.UserRole;
import com.hdev.user_service.mapper.UserMapper;
import com.hdev.user_service.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Service
public class UserService {
    private static String NOT_FOUND = "Resource Not Found";
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional(readOnly = true)
    public UserDTO getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(NOT_FOUND));
        return UserMapper.toUserDTO(user);
    }

    @Transactional(readOnly = true)
    public List<UserDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream().map(user -> UserMapper.toUserDTO(user)).toList();
    }

    @Transactional
    public UserDTO createUser(UserRequestDTO dto) {
        User user = UserMapper.toEntity(dto, new User());
        user.setRole(UserRole.CUSTOMER);
        user.setCreatedAt(Instant.now());
        userRepository.save(user);
        return UserMapper.toUserDTO(user);
    }

    @Transactional
    public UserDTO updateUser(Long id, UserRequestDTO dto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(NOT_FOUND));
        user = UserMapper.toEntity(dto, user);
        user.setUpdatedAt(Instant.now());
        userRepository.save(user);
        return UserMapper.toUserDTO(user);
    }
}
