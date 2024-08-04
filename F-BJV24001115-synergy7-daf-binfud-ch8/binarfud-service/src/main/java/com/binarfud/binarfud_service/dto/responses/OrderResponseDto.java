package com.binarfud.binarfud_service.dto.responses;

import lombok.Data;
import lombok.NonNull;

import java.time.LocalDateTime;

@Data
public class OrderResponseDto {
    @NonNull
    private LocalDateTime orderTime;

    @NonNull
    private String destinationAddress;

    @NonNull
    private boolean completed;
}
