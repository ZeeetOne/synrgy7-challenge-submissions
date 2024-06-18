package com.example.binarfud.payload;

import com.example.binarfud.model.Product;
import lombok.Data;

import java.util.UUID;

@Data
public class ProductDto {
    private UUID id;
    private String productName;
    private Long price;
    private UUID merchant_id;
    private Product.ProductCategory category;
}
