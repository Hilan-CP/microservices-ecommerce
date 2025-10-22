package com.hdev.user_service.mapper;

import com.hdev.user_service.dto.AddressDTO;
import com.hdev.user_service.entity.Address;

public class AddressMapper {
    public static Address toEntity(AddressDTO dto, Address entity){
        entity.setStreet(dto.getStreet());
        entity.setCity(dto.getCity());
        entity.setState(dto.getState());
        entity.setCountry(dto.getCountry());
        entity.setZipcode(dto.getZipcode());
        return entity;
    }

    public static AddressDTO toAddressDTO(Address entity){
        return new AddressDTO(
                entity.getStreet(),
                entity.getCity(),
                entity.getState(),
                entity.getCountry(),
                entity.getZipcode());
    }
}
