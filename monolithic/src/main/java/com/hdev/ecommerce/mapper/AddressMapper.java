package com.hdev.ecommerce.mapper;

import com.hdev.ecommerce.dto.AddressDTO;
import com.hdev.ecommerce.entity.Address;

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
