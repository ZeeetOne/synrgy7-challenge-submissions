package com.example.binarfud.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class OrderDto {
    private UUID id;
    private LocalDateTime orderTime;
    private String destinationAddress;
    private boolean completed;
    private UUID userId;
}
