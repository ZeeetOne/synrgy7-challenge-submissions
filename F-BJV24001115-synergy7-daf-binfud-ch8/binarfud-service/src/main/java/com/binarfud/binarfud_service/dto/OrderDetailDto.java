package com.binarfud.binarfud_service.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.util.UUID;

@Data
public class OrderDetailDto {
    @NotNull(message = "ID must not be null")
    private UUID id;

    @NotNull(message = "Order ID must not be null")
    private UUID orderId;

    @NotNull(message = "Product ID must not be null")
    private UUID productId;

    @Positive(message = "Quantity must be positive")
    private int quantity;

    @NotNull(message = "Total price must not be null")
    private Long totalPrice;
}
