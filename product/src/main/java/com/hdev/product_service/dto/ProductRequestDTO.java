package com.hdev.product_service.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ProductRequestDTO {
    private String name;
    private String description;
    private BigDecimal price;
    private Integer stock;
    private String imgUrl;
    private List<Long> categoryIds = new ArrayList<>();

    public ProductRequestDTO() {
    }

    public ProductRequestDTO(String name, String description, BigDecimal price, Integer stock, String imgUrl) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.stock = stock;
        this.imgUrl = imgUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public List<Long> getCategoryIds() {
        return categoryIds;
    }
}
