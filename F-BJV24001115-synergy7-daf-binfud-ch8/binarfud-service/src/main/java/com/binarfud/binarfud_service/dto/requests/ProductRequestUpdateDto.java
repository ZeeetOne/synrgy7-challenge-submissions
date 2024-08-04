package com.binarfud.binarfud_service.dto.requests;

import com.binarfud.binarfud_service.entity.Product;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class ProductRequestUpdateDto {
    @NotBlank(message = "Product name must not be blank")
    private String productName;

    @NotNull(message = "Price must not be null")
    @Positive(message = "Price must be positive")
    private Long price;

    @NotNull(message = "Category must not be null")
    private Product.ProductCategory category;
}
