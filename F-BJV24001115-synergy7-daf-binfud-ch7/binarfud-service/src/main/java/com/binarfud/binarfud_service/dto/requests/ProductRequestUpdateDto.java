package com.binarfud.binarfud_service.dto.requests;

import com.binarfud.binarfud_service.entity.Product;
import lombok.Data;

@Data
public class ProductRequestUpdateDto {
    private String productName;
    private Long price;
    private Product.ProductCategory category;
}
