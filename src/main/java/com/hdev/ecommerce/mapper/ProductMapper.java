package com.hdev.ecommerce.mapper;

import com.hdev.ecommerce.dto.ProductDTO;
import com.hdev.ecommerce.dto.ProductRequestDTO;
import com.hdev.ecommerce.entity.Category;
import com.hdev.ecommerce.entity.Product;

public class ProductMapper {
    public static Product toEntity(ProductRequestDTO dto, Product entity){
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setPrice(dto.getPrice());
        entity.setStock(dto.getStock());
        entity.setImgUrl(dto.getImgUrl());
        return entity;
    }

    public static ProductDTO toDto(Product entity){
        ProductDTO dto = new ProductDTO(
                entity.getId(),
                entity.getName(),
                entity.getDescription(),
                entity.getPrice(),
                entity.getStock(),
                entity.getImgUrl(),
                entity.isActive(),
                entity.getCreatedAt(),
                entity.getUpdatedAt());
        dto.getCategories().addAll(entity.getCategories().stream().map(Category::getName).toList());
        return dto;
    }
}
