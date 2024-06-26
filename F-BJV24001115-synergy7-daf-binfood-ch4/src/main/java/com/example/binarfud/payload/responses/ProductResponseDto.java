package com.example.binarfud.payload.responses;

import com.example.binarfud.model.Product;
import lombok.Data;

@Data
public class ProductResponseDto {
    private String productName;
    private Long price;
    private Product.ProductCategory category;
}
