package com.hdev.product_service.controller;

import com.hdev.product_service.dto.ProductDTO;
import com.hdev.product_service.dto.ProductRequestDTO;
import com.hdev.product_service.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    @GetMapping("/search")
    public ResponseEntity<List<ProductDTO>> getProductByName(@RequestParam(required = true) String name){
        List<ProductDTO> products = service.getProductsByName(name);
        return ResponseEntity.ok(products);
    }

    @GetMapping
    public ResponseEntity<List<ProductDTO>> getAllProducts(){
        List<ProductDTO> products = service.getAllProducts();
        return ResponseEntity.ok(products);
    }

    @PostMapping
    public ResponseEntity<ProductDTO> createProduct(@RequestBody ProductRequestDTO dto){
        ProductDTO product = service.createProduct(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").build(product.getId());
        return ResponseEntity.created(uri).body(product);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable Long id, @RequestBody ProductRequestDTO dto){
        ProductDTO product = service.updateProduct(id, dto);
        return ResponseEntity.ok(product);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id){
        service.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}
