package com.example.binarfud.dto.requests;

import com.example.binarfud.entity.Product;
import lombok.Data;

@Data
public class ProductRequestUpdateDto {
    private String productName;
    private Long price;
    private Product.ProductCategory category;
}
