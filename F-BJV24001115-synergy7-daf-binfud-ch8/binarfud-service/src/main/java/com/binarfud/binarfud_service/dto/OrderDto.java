package com.binarfud.binarfud_service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class OrderDto {
    @NotNull(message = "ID must not be null")
    private UUID id;

    @NotNull(message = "Order time must not be null")
    private LocalDateTime orderTime;

    @NotBlank(message = "Destination address must not be blank")
    private String destinationAddress;

    private boolean isCompleted;

    @NotNull(message = "User ID must not be null")
    private UUID userId;
}
