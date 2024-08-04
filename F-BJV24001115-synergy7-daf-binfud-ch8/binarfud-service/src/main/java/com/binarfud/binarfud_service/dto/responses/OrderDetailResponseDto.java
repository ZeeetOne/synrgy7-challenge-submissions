package com.binarfud.binarfud_service.dto.responses;

import lombok.Data;
import lombok.NonNull;

import java.util.UUID;

@Data
public class OrderDetailResponseDto {
    @NonNull
    private UUID productId;

    @NonNull
    private int quantity;

    @NonNull
    private Long totalPrice;
}
