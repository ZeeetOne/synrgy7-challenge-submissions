package com.binarfud.binarfud_service.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class OrderDetailDto {
    private UUID id;
    private UUID orderId;
    private UUID productId;
    private int quantity;
    private Long totalPrice;
}
