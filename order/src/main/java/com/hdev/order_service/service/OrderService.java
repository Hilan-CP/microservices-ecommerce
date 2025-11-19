package com.hdev.order_service.service;

import com.hdev.order_service.dto.CartItemDTO;
import com.hdev.order_service.dto.OrderDTO;
import com.hdev.order_service.entity.Order;
import com.hdev.order_service.mapper.OrderMapper;
import com.hdev.order_service.repository.OrderRepository;
import com.hdev.order_service.entity.OrderItem;
import com.hdev.order_service.entity.OrderStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final CartItemService cartItemService;

    public OrderService(OrderRepository orderRepository, CartItemService cartItemService) {
        this.orderRepository = orderRepository;
        this.cartItemService = cartItemService;
    }

    @Transactional
    public OrderDTO createOrder(String userId) {
        List<CartItemDTO> cartItems = cartItemService.getUserCart(userId);
        if (cartItems.isEmpty()) {
            throw new IllegalArgumentException("cart is empty");
        }
        Order order = new Order();
        order.setUserId(userId);
        order.setStatus(OrderStatus.PENDING);
        order.setCreatedAt(Instant.now());
        for (CartItemDTO item : cartItems) {
            OrderItem orderItem = new OrderItem();
            order.getItems().add(orderItem);
            orderItem.setOrder(order);
            orderItem.setPrice(item.getPrice());
            orderItem.setQuantity(item.getQuantity());
            orderItem.setProductId(item.getProductId());
        }
        BigDecimal total = order.getItems().stream()
                .map(item -> item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        order.setTotal(total);
        order = orderRepository.save(order);
        cartItemService.clearUserCart(userId);
        return OrderMapper.toDto(order);
    }
}
