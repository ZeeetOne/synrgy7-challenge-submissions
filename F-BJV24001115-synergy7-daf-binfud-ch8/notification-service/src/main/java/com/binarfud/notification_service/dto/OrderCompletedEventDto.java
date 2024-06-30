package com.binarfud.notification_service.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
public class OrderCompletedEventDto {
    private UUID id;
    private LocalDateTime orderTime;
    private String userEmail;
    private List<OrderCompletedDetailDto> orderDetails;

    @Data
    public static class OrderCompletedDetailDto {
        private UUID productId;
        private String productName;
        private int quantity;
        private double price;
    }
}
