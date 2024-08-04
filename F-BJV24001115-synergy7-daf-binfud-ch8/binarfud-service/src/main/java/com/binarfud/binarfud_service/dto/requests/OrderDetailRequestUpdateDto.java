package com.binarfud.binarfud_service.dto.requests;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class OrderDetailRequestUpdateDto {
    @NotNull(message = "Product ID must not be null")
    private UUID productId;

    @Min(value = 1, message = "Quantity must be at least 1")
    @Max(value = 10, message = "Quantity must be at most 10")
    private int quantity;
}
