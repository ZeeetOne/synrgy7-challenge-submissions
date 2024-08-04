package com.binarfud.binarfud_service.dto.requests;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class OrderRequestUpdateDto {
    @NotBlank(message = "Destination address must not be blank")
    private String destinationAddress;
}
