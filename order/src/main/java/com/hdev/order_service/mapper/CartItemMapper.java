package com.hdev.order_service.mapper;

import com.hdev.order_service.dto.CartItemDTO;
import com.hdev.order_service.entity.CartItem;

public class CartItemMapper {
    public static CartItemDTO toDto(CartItem entity) {
        return new CartItemDTO(
                entity.getId(),
                entity.getUserId(),
                entity.getProductId(),
                entity.getPrice(),
                entity.getQuantity(),
                entity.getCreatedAt(),
                entity.getUpdatedAt());
    }
}
