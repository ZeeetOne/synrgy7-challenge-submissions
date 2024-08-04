package com.binarfud.binarfud_service.dto.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class OrderRequestCreateDto {
    @NotNull(message = "User ID must not be null")
    private UUID userId;

    @NotBlank(message = "Destination address must not be blank")
    private String destinationAddress;
}
