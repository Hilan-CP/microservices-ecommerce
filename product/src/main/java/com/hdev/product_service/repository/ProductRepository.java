package com.hdev.product_service.repository;

import com.hdev.product_service.entity.Product;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @EntityGraph(attributePaths = {"categories"})
    List<Product> findByNameContainingIgnoreCase(String name);
}
