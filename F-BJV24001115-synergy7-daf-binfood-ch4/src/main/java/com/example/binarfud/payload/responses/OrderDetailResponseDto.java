package com.example.binarfud.payload.responses;

import lombok.Data;

import java.util.UUID;

@Data
public class OrderDetailResponseDto {
    private UUID productId;
    private int quantity;
    private Long totalPrice;
}
