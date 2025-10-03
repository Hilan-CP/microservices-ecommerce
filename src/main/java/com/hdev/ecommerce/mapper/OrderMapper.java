package com.hdev.ecommerce.mapper;

import com.hdev.ecommerce.dto.OrderDTO;
import com.hdev.ecommerce.entity.Order;

public class OrderMapper {
    public static OrderDTO toDto(Order entity){
        OrderDTO dto = new OrderDTO(
                entity.getId(),
                entity.getTotal(),
                entity.getStatus(),
                entity.getUser().getId(),
                entity.getCreatedAt(),
                entity.getUpdatedAt());
        dto.getItems().addAll(
                entity.getItems().stream()
                        .map(item -> OrderItemMapper.toDto(item))
                        .toList());
        return dto;
    }
}
