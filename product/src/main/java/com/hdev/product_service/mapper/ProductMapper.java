package com.hdev.product_service.mapper;

import com.hdev.product_service.dto.ProductDTO;
import com.hdev.product_service.dto.ProductRequestDTO;
import com.hdev.product_service.entity.Category;
import com.hdev.product_service.entity.Product;

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
