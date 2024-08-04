package com.binarfud.binarfud_service.dto.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class OrderRequestCreateDtos {
    private UUID userId;
    private String destinationAddress;
    private List<OrderDetailRequestCreateDtos> orderDetails;

    @Data
    public static class OrderDetailRequestCreateDtos {
        private UUID productId;
        private int quantity;
    }
}

