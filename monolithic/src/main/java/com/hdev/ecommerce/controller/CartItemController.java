package com.hdev.ecommerce.controller;

import com.hdev.ecommerce.dto.CartItemDTO;
import com.hdev.ecommerce.dto.CartItemRequestDTO;
import com.hdev.ecommerce.service.CartItemService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart")
public class CartItemController {
    private final CartItemService service;

    public CartItemController(CartItemService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<CartItemDTO> addItem(@RequestHeader(name = "X-User-Id") Long userId, @RequestBody CartItemRequestDTO dto){
        CartItemDTO item = service.addItem(userId, dto);
        return ResponseEntity.ok(item);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> removeItem(@RequestHeader(name = "X-User-Id") Long userId, @PathVariable Long productId){
        service.removeItem(userId, productId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<CartItemDTO>> getUserCart(@RequestHeader(name = "X-User-Id") Long userId){
        List<CartItemDTO> items = service.getUserCart(userId);
        return ResponseEntity.ok(items);
    }

    @DeleteMapping
    public ResponseEntity<Void> clearUserCart(@RequestHeader(name = "X-User-Id") Long userId){
        service.clearUserCart(userId);
        return ResponseEntity.noContent().build();
    }
}
