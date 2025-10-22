package com.hdev.ecommerce.mapper;

import com.hdev.ecommerce.dto.OrderItemDTO;
import com.hdev.ecommerce.entity.CartItem;
import com.hdev.ecommerce.entity.OrderItem;

public class OrderItemMapper {
    public static OrderItemDTO toDto(OrderItem entity){
        return new OrderItemDTO(
                entity.getPrice(),
                entity.getQuantity(),
                entity.getProduct().getId());
    }
}
