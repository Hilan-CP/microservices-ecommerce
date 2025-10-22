package com.hdev.ecommerce.mapper;

import com.hdev.ecommerce.dto.CartItemDTO;
import com.hdev.ecommerce.dto.CartItemRequestDTO;
import com.hdev.ecommerce.entity.CartItem;

public class CartItemMapper {
    public static CartItemDTO toDto(CartItem entity) {
        return new CartItemDTO(
                entity.getId(),
                entity.getUser().getId(),
                entity.getProduct().getId(),
                entity.getPrice(),
                entity.getQuantity(),
                entity.getCreated_at(),
                entity.getUpdated_at());
    }
}
