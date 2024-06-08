package com.example.binarfud.dto.requests;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.UUID;

@Data
public class OrderDetailRequestCreateDto {
    @NotBlank
    private UUID orderId;

    @NotBlank
    private UUID productId;

    @NotBlank
    @Min(1)
    @Max(10)
    private int quantity;
}
