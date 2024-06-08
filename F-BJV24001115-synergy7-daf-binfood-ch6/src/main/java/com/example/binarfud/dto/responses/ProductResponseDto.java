package com.example.binarfud.dto.responses;

import com.example.binarfud.entity.Product;
import lombok.Data;

@Data
public class ProductResponseDto {
    private String productName;
    private Long price;
    private Product.ProductCategory category;
}
