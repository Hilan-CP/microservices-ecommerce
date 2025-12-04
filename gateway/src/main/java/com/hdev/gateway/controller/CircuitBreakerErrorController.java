package com.hdev.gateway.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/errors")
public class CircuitBreakerErrorController {

    @GetMapping("/users")
    public ResponseEntity<String> userFallback(){
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body("User Service is currently unavailable. Please try again later.");
    }

    @GetMapping("/products")
    public ResponseEntity<String> productFallback(){
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body("Product Service is currently unavailable. Please try again later.");
    }

    @GetMapping("/orders")
    public ResponseEntity<String> orderFallback(){
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body("Order Service is currently unavailable. Please try again later.");
    }
}
