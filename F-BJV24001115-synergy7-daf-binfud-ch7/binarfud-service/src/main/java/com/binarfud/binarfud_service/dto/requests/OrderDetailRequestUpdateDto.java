package com.binarfud.binarfud_service.dto.requests;

import lombok.Data;

import java.util.UUID;

@Data
public class OrderDetailRequestUpdateDto {
    private UUID productId;
    private int quantity;
}
