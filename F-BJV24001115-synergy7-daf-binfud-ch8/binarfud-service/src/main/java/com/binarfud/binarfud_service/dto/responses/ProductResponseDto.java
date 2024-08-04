package com.binarfud.binarfud_service.dto.responses;

import com.binarfud.binarfud_service.entity.Product;
import lombok.Data;

@Data
public class ProductResponseDto {
    private String productName;

    private Long price;

    private Product.ProductCategory category;
}
