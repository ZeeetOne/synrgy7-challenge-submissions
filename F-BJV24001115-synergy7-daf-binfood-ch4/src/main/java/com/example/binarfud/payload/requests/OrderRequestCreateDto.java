package com.example.binarfud.payload.requests;

import lombok.Data;

import java.util.UUID;

@Data
public class OrderRequestCreateDto {
    private UUID user_id;
    private String destinationAddress;
}
