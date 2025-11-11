package com.hdev.user_service.mapper;

import com.hdev.user_service.dto.UserDTO;
import com.hdev.user_service.dto.UserRequestDTO;
import com.hdev.user_service.entity.Address;
import com.hdev.user_service.entity.User;

public class UserMapper {
    public static User toEntity(UserRequestDTO dto, User entity){
        entity.setFirstName(dto.getFirstName());
        entity.setLastName(dto.getLastName());
        entity.setEmail(dto.getEmail());
        entity.setPhone(dto.getPhone());
        entity.setAddress(AddressMapper.toEntity(dto.getAddress(), new Address()));
        return entity;
    }

    public static UserDTO toUserDTO(User entity){
        return new UserDTO(
                entity.getId(),
                entity.getFirstName(),
                entity.getLastName(),
                entity.getEmail(),
                entity.getPhone(),
                entity.getRole(),
                AddressMapper.toAddressDTO(entity.getAddress()),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }
}
