package com.binarfud.binarfud_service.dto.responses;

import lombok.Data;
import lombok.NonNull;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
public class OrderResponseDtos {
    private LocalDateTime orderTime;
    private String destinationAddress;
    private boolean isCompleted;
    private List<OrderDetailResponseCreateDtos> orderDetails;

    @Data
    public static class OrderDetailResponseCreateDtos {
        private UUID productId;
        private int quantity;
        private Long price;
    }
}
