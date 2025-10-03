package com.hdev.ecommerce.service;

import com.hdev.ecommerce.dto.CartItemDTO;
import com.hdev.ecommerce.dto.CartItemRequestDTO;
import com.hdev.ecommerce.entity.CartItem;
import com.hdev.ecommerce.entity.Product;
import com.hdev.ecommerce.entity.User;
import com.hdev.ecommerce.mapper.CartItemMapper;
import com.hdev.ecommerce.repository.CartItemRepository;
import com.hdev.ecommerce.repository.ProductRepository;
import com.hdev.ecommerce.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
public class CartItemService {
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public CartItemService(CartItemRepository cartItemRepository, ProductRepository productRepository, UserRepository userRepository) {
        this.cartItemRepository = cartItemRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public CartItemDTO addItem(Long userId, CartItemRequestDTO dto){
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
        Optional<CartItem> existingItem = cartItemRepository.findByUserAndProduct(user, product);
        CartItem item;
        if(existingItem.isPresent()){
            item = existingItem.get();
            item.setQuantity(item.getQuantity() + dto.getQuantity());
            item.setUpdated_at(Instant.now());
        }
        else{
            item = new CartItem();
            item.setQuantity(dto.getQuantity());
            item.setPrice(product.getPrice());
            item.setProduct(product);
            item.setUser(user);
            item.setCreated_at(Instant.now());
        }
        item = cartItemRepository.save(item);
        return CartItemMapper.toDto(item);
    }

    @Transactional
    public void removeItem(Long userId, Long productId){
        User user = new User(userId, null, null, null, null, null, null, null, null);
        Product product = new Product(productId, null, null, null, null, null, true, null, null);
        CartItem item = cartItemRepository.findByUserAndProduct(user, product)
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
