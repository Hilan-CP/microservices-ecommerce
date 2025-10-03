package com.hdev.ecommerce.service;

import com.hdev.ecommerce.dto.CartItemDTO;
import com.hdev.ecommerce.dto.OrderDTO;
import com.hdev.ecommerce.entity.*;
import com.hdev.ecommerce.mapper.OrderMapper;
import com.hdev.ecommerce.repository.OrderRepository;
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
    public OrderDTO createOrder(Long userId) {
        List<CartItemDTO> cartItems = cartItemService.getUserCart(userId);
        if (cartItems.isEmpty()) {
            throw new IllegalArgumentException("cart is empty");
        }
        Order order = new Order();
        User user = new User();
        user.setId(userId);
        order.setUser(user);
        order.setStatus(OrderStatus.PENDING);
        order.setCreatedAt(Instant.now());
        for (CartItemDTO item : cartItems) {
            OrderItem orderItem = new OrderItem();
            order.getItems().add(orderItem);
            orderItem.setOrder(order);
            orderItem.setPrice(item.getPrice());
            orderItem.setQuantity(item.getQuantity());
            Product product = new Product();
            product.setId(item.getProductId());
            orderItem.setProduct(product);
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
