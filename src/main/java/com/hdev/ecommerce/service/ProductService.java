package com.hdev.ecommerce.service;

import com.hdev.ecommerce.dto.ProductDTO;
import com.hdev.ecommerce.dto.ProductRequestDTO;
import com.hdev.ecommerce.entity.Product;
import com.hdev.ecommerce.mapper.ProductMapper;
import com.hdev.ecommerce.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<ProductDTO> getProductsByName(String name){
        List<Product> products = productRepository.findByNameContainingIgnoreCase(name);
        return products.stream().map(product -> ProductMapper.toDto(product)).toList();
    }

    public List<ProductDTO> getAllProducts(){
        List<Product> products = productRepository.findAll();
        return products.stream().map(product -> ProductMapper.toDto(product)).toList();
    }

    public ProductDTO createProduct(ProductRequestDTO dto){
        Product product = ProductMapper.toEntity(dto, new Product());
        product.setActive(true);
        product.setCreatedAt(Instant.now());
        product = productRepository.save(product);
        return ProductMapper.toDto(product);
    }

    public ProductDTO updateProduct(Long id, ProductRequestDTO dto){
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found"));
        product = ProductMapper.toEntity(dto, product);
        product.setUpdatedAt(Instant.now());
        product = productRepository.save(product);
        return ProductMapper.toDto(product);
    }

    public void deleteProdcut(Long id){
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found"));
        product.setActive(false);
        product.setUpdatedAt(Instant.now());
        productRepository.save(product);
    }
}
