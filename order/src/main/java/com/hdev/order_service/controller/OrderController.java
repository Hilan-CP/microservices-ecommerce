package com.hdev.order_service.controller;

import com.hdev.order_service.dto.OrderDTO;
import com.hdev.order_service.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/orders")
public class OrderController {
    private final OrderService service;

    public OrderController(OrderService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<OrderDTO> createOrder(@RequestHeader(name = "X-User-Id") String userId){
        OrderDTO order = service.createOrder(userId);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").build(order.getId());
        return ResponseEntity.created(uri).body(order);
    }
}
