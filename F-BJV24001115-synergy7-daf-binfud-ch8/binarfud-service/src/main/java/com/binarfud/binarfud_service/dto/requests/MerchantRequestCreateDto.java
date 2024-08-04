package com.binarfud.binarfud_service.dto.requests;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class MerchantRequestCreateDto {
    @NotBlank(message = "Merchant name must not be blank")
    private String merchantName;

    @NotBlank(message = "Merchant location must not be blank")
    private String merchantLocation;
}
