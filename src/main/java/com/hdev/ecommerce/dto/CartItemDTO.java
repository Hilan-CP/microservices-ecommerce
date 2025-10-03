package com.hdev.ecommerce.dto;

import java.math.BigDecimal;
import java.time.Instant;

public class CartItemDTO {
    private Long id;
    private Long userId;
    private Long productId;
    private BigDecimal price;
    private Integer quantity;
    private Instant created_at;
    private Instant updated_at;

    public CartItemDTO() {
    }

    public CartItemDTO(Long id, Long userId, Long productId, BigDecimal price, Integer quantity, Instant created_at, Instant updated_at) {
        this.id = id;
        this.userId = userId;
        this.productId = productId;
        this.price = price;
        this.quantity = quantity;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Instant getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Instant created_at) {
        this.created_at = created_at;
    }

    public Instant getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Instant updated_at) {
        this.updated_at = updated_at;
    }
}
