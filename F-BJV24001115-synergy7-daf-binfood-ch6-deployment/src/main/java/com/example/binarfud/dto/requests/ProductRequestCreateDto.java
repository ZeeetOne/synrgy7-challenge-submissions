package com.example.binarfud.dto.requests;

import com.example.binarfud.entity.Product;
import lombok.Data;

import java.util.UUID;

@Data
public class ProductRequestCreateDto {
    private String productName;
    private Long price;
    private UUID merchant_id;
    private Product.ProductCategory category;
}
