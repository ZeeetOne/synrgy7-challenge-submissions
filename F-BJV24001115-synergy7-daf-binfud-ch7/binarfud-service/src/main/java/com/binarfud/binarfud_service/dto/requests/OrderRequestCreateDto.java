package com.binarfud.binarfud_service.dto.requests;

import lombok.Data;

import java.util.UUID;

@Data
public class OrderRequestCreateDto {
    private UUID userId;
    private String destinationAddress;
}
