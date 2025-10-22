package com.hdev.product_service.mapper;

import com.hdev.product_service.dto.CategoryDTO;
import com.hdev.product_service.entity.Category;

public class CategoryMapper {
    public static Category toEntity(CategoryDTO dto, Category entity){
        entity.setName(dto.getName());
        return entity;
    }

    public static CategoryDTO toDto(Category entity){
        return new CategoryDTO(entity.getId(), entity.getName());
    }
}
