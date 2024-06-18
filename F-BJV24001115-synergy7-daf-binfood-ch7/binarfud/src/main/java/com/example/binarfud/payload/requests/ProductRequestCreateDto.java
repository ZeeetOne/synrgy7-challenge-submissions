package com.example.binarfud.payload.requests;

import com.example.binarfud.model.Product;
import lombok.Data;

import java.util.UUID;

@Data
public class ProductRequestCreateDto {
    private String productName;
    private Long price;
    private UUID merchant_id;
    private Product.ProductCategory category;
}
