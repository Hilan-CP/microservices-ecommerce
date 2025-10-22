package com.hdev.order_service.service;

import com.hdev.order_service.dto.CartItemDTO;
import com.hdev.order_service.dto.CartItemRequestDTO;
import com.hdev.order_service.entity.CartItem;
import com.hdev.order_service.mapper.CartItemMapper;
import com.hdev.order_service.repository.CartItemRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
public class CartItemService {
    private final CartItemRepository cartItemRepository;

    public CartItemService(CartItemRepository cartItemRepository) {
        this.cartItemRepository = cartItemRepository;
    }

    @Transactional
    public CartItemDTO addItem(Long userId, CartItemRequestDTO dto){
        /*
        Product product = productRepository.findById(dto.getProductId())
                .orElseThrow(() -> new IllegalArgumentException("product not found"));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("user not found"));
        if(!product.isActive()){
            throw new IllegalArgumentException("product is inactive");
        }
        if(product.getStock() < dto.getQuantity()){
            throw new IllegalArgumentException("product stock is not enough");
        }
        */
        Optional<CartItem> existingItem = cartItemRepository.findByUserIdAndProductId(userId, dto.getProductId());
        CartItem item;
        if(existingItem.isPresent()){
            item = existingItem.get();
            item.setQuantity(item.getQuantity() + dto.getQuantity());
            item.setUpdated_at(Instant.now());
        }
        else{
            item = new CartItem();
            item.setQuantity(dto.getQuantity());
            item.setPrice(BigDecimal.valueOf(100.00));
            item.setProductId(dto.getProductId());
            item.setUserId(userId);
            item.setCreated_at(Instant.now());
        }
        item = cartItemRepository.save(item);
        return CartItemMapper.toDto(item);
    }

    @Transactional
    public void removeItem(Long userId, Long productId){
        CartItem item = cartItemRepository.findByUserIdAndProductId(userId, productId)
                .orElseThrow(() -> new IllegalArgumentException("no items found for current user"));
        cartItemRepository.delete(item);
    }

    @Transactional(readOnly = true)
    public List<CartItemDTO> getUserCart(Long userId){
        List<CartItem> items = cartItemRepository.findByUserId(userId);
        return items.stream().map(item -> CartItemMapper.toDto(item)).toList();
    }

    @Transactional
    public void clearUserCart(Long userId){
        List<CartItem> items = cartItemRepository.findByUserId(userId);
        if(items.isEmpty()){
            throw new IllegalArgumentException("no items found for current user");
        }
        cartItemRepository.deleteAll(items);
    }
}
