package com.example.binarfud.payload.requests;

import com.example.binarfud.model.Product;
import lombok.Data;

@Data
public class ProductRequestUpdateDto {
    private String productName;
    private Long price;
    private Product.ProductCategory category;
}
