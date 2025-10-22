package com.hdev.order_service.mapper;

import com.hdev.order_service.dto.OrderDTO;
import com.hdev.order_service.entity.Order;

public class OrderMapper {
    public static OrderDTO toDto(Order entity){
        OrderDTO dto = new OrderDTO(
                entity.getId(),
                entity.getTotal(),
                entity.getStatus(),
                entity.getUserId(),
                entity.getCreatedAt(),
                entity.getUpdatedAt());
        dto.getItems().addAll(
                entity.getItems().stream()
                        .map(item -> OrderItemMapper.toDto(item))
                        .toList());
        return dto;
    }
}
