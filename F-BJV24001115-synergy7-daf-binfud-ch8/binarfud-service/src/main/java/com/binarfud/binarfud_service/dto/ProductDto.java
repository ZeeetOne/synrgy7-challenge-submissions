package com.binarfud.binarfud_service.dto;

import com.binarfud.binarfud_service.entity.Product;
import lombok.Data;

import java.util.UUID;

@Data
public class ProductDto {
    private UUID id;
    private String productName;
    private Long price;
    private UUID merchantId;
    private Product.ProductCategory category;
}
