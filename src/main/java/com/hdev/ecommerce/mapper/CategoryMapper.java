package com.hdev.ecommerce.mapper;

import com.hdev.ecommerce.dto.CategoryDTO;
import com.hdev.ecommerce.entity.Category;

public class CategoryMapper {
    public static Category toEntity(CategoryDTO dto, Category entity){
        entity.setName(dto.getName());
        return entity;
    }

    public static CategoryDTO toDto(Category entity){
        return new CategoryDTO(entity.getId(), entity.getName());
    }
}
