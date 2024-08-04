package com.binarfud.binarfud_service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class MerchantDto {
    @NotNull(message = "ID must not be null")
    private UUID id;

    @NotBlank(message = "Merchant name must not be blank")
    private String merchantName;

    @NotBlank(message = "Merchant location must not be blank")
    private String merchantLocation;

    private boolean isOpen;
}
