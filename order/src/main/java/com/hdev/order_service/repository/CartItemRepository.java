package com.hdev.order_service.repository;

import com.hdev.order_service.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    Optional<CartItem> findByUserIdAndProductId(String userId, Long productId);

    List<CartItem> findByUserId(String userId);
}
