package com.hdev.order_service.controller;

import com.hdev.order_service.dto.CartItemDTO;
import com.hdev.order_service.dto.CartItemRequestDTO;
import com.hdev.order_service.service.CartItemService;
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
    public ResponseEntity<CartItemDTO> addItem(@RequestHeader(name = "X-User-Id") String userId, @RequestBody CartItemRequestDTO dto){
        CartItemDTO item = service.addItem(userId, dto);
        return ResponseEntity.ok(item);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> removeItem(@RequestHeader(name = "X-User-Id") String userId, @PathVariable Long productId){
        service.removeItem(userId, productId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<CartItemDTO>> getUserCart(@RequestHeader(name = "X-User-Id") String userId){
        List<CartItemDTO> items = service.getUserCart(userId);
        return ResponseEntity.ok(items);
    }

    @DeleteMapping
    public ResponseEntity<Void> clearUserCart(@RequestHeader(name = "X-User-Id") String userId){
        service.clearUserCart(userId);
        return ResponseEntity.noContent().build();
    }
}
