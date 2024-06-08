package com.example.binarfud.dto.responses;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OrderResponseDto {
    private LocalDateTime orderTime;
    private String destinationAddress;
    private boolean completed;
}
