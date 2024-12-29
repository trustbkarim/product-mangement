package com.product.dao.dto;

import lombok.Data;

@Data
public class ProductDto {

    private Long id;
    private String code;
    private String name;
    private String description;
    private String image;
    private String category;
    private double price;
    private int quantity;
    private String internalReference;
    private Long shellId;
    private String inventoryStatus;
    private double rating;
}

