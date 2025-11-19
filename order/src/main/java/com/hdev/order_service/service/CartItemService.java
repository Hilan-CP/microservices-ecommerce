package com.hdev.order_service.service;

import com.hdev.order_service.dto.CartItemDTO;
import com.hdev.order_service.dto.CartItemRequestDTO;
import com.hdev.order_service.dto.ProductDTO;
import com.hdev.order_service.entity.CartItem;
import com.hdev.order_service.mapper.CartItemMapper;
import com.hdev.order_service.repository.CartItemRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
public class CartItemService {
    private static final String PRODUCT_HOST = "http://localhost:8082";
    private static final String USER_HOST = "http://localhost:8081";
    private final CartItemRepository cartItemRepository;
    private final RestTemplate restTemplate;

    public CartItemService(CartItemRepository cartItemRepository) {
        this.cartItemRepository = cartItemRepository;
        this.restTemplate = new RestTemplate();
    }

    @Transactional
    public CartItemDTO addItem(String userId, CartItemRequestDTO dto){
        ResponseEntity<ProductDTO> productResponse = restTemplate.getForEntity(PRODUCT_HOST + "/products/{productId}", ProductDTO.class, dto.getProductId());
        if(productResponse.getStatusCode().value() != 200){
            throw new IllegalArgumentException("product not found");
        }
        ProductDTO product = productResponse.getBody();
        if(!product.isActive()){
            throw new IllegalArgumentException("product is inactive");
        }
        if(product.getStock() < dto.getQuantity()){
            throw new IllegalArgumentException("product stock is not enough");
        }
        ResponseEntity<Object> userResponse = restTemplate.getForEntity(USER_HOST + "/users/{userId}", Object.class, userId);
        if(userResponse.getStatusCode().value() != 200){
            throw new IllegalArgumentException("user not found");
        }
        Optional<CartItem> existingItem = cartItemRepository.findByUserIdAndProductId(userId, dto.getProductId());
        CartItem item;
        if(existingItem.isPresent()){
            item = existingItem.get();
            item.setQuantity(item.getQuantity() + dto.getQuantity());
            item.setUpdatedAt(Instant.now());
        }
        else{
            item = new CartItem();
            item.setQuantity(dto.getQuantity());
            item.setPrice(product.getPrice());
            item.setProductId(dto.getProductId());
            item.setUserId(userId);
            item.setCreatedAt(Instant.now());
        }
        item = cartItemRepository.save(item);
        return CartItemMapper.toDto(item);
    }

    @Transactional
    public void removeItem(String userId, Long productId){
        CartItem item = cartItemRepository.findByUserIdAndProductId(userId, productId)
                .orElseThrow(() -> new IllegalArgumentException("no items found for current user"));
        cartItemRepository.delete(item);
    }

    @Transactional(readOnly = true)
    public List<CartItemDTO> getUserCart(String userId){
        List<CartItem> items = cartItemRepository.findByUserId(userId);
        return items.stream().map(item -> CartItemMapper.toDto(item)).toList();
    }

    @Transactional
    public void clearUserCart(String userId){
        List<CartItem> items = cartItemRepository.findByUserId(userId);
        if(items.isEmpty()){
            throw new IllegalArgumentException("no items found for current user");
        }
        cartItemRepository.deleteAll(items);
    }
}
