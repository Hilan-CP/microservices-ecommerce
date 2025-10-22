package com.hdev.order_service.mapper;

import com.hdev.order_service.dto.OrderItemDTO;
import com.hdev.order_service.entity.OrderItem;

public class OrderItemMapper {
    public static OrderItemDTO toDto(OrderItem entity){
        return new OrderItemDTO(
                entity.getPrice(),
                entity.getQuantity(),
                entity.getProductId());
    }
}
