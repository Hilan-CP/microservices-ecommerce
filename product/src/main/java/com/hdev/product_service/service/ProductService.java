package com.hdev.product_service.service;

import com.hdev.product_service.dto.ProductDTO;
import com.hdev.product_service.dto.ProductRequestDTO;
import com.hdev.product_service.entity.Category;
import com.hdev.product_service.entity.Product;
import com.hdev.product_service.mapper.ProductMapper;
import com.hdev.product_service.repository.CategoryRepository;
import com.hdev.product_service.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @Transactional(readOnly = true)
    public ProductDTO getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found"));
        return ProductMapper.toDto(product);
    }

    @Transactional(readOnly = true)
    public List<ProductDTO> getProductsByName(String name){
        List<Product> products = productRepository.findByNameContainingIgnoreCase(name);
        return products.stream().map(product -> ProductMapper.toDto(product)).toList();
    }

    @Transactional(readOnly = true)
    public List<ProductDTO> getAllProducts(){
        List<Product> products = productRepository.findAll();
        return products.stream().map(product -> ProductMapper.toDto(product)).toList();
    }

    @Transactional
    public ProductDTO createProduct(ProductRequestDTO dto){
        Product product = ProductMapper.toEntity(dto, new Product());
        product.setActive(true);
        product.setCreatedAt(Instant.now());
        addProductCategories(product, dto);
        product = productRepository.save(product);
        return ProductMapper.toDto(product);
    }

    @Transactional
    public ProductDTO updateProduct(Long id, ProductRequestDTO dto){
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found"));
        product = ProductMapper.toEntity(dto, product);
        product.setUpdatedAt(Instant.now());
        addProductCategories(product, dto);
        product = productRepository.save(product);
        return ProductMapper.toDto(product);
    }

    @Transactional
    public void deleteProduct(Long id){
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found"));
        product.setActive(false);
        product.setUpdatedAt(Instant.now());
        productRepository.save(product);
    }

    private void addProductCategories(Product entity, ProductRequestDTO dto){
        entity.getCategories().clear();
        for(Long categoryId : dto.getCategoryIds()){
            Category category = categoryRepository.findById(categoryId)
                    .orElseThrow(() -> new IllegalArgumentException("category not found"));
            entity.getCategories().add(category);
        }
    }
}
