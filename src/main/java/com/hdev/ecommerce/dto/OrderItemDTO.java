package com.hdev.ecommerce.dto;

import java.math.BigDecimal;

public class OrderItemDTO {
    private BigDecimal price;
    private Integer quantity;
    private Long productId;

    public OrderItemDTO() {
    }

    public OrderItemDTO(BigDecimal price, Integer quantity, Long productId) {
        this.price = price;
        this.quantity = quantity;
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

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }
}
