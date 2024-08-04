package com.binarfud.binarfud_service.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
public class OrderCompletedEventDto {
    @NotNull(message = "ID must not be null")
    private UUID id;

    @NotNull(message = "Order time must not be null")
    private LocalDateTime orderTime;

    @Email(message = "Email should be valid")
    @NotBlank(message = "User email must not be blank")
    private String userEmail;

    @NotEmpty(message = "Order details must not be empty")
    private List<OrderCompletedDetailDto> orderDetails;

    @Data
    public static class OrderCompletedDetailDto {
        @NotNull(message = "Product ID must not be null")
        private UUID productId;

        @NotBlank(message = "Product name must not be blank")
        private String productName;

        @NotNull(message = "Quantity must not be null")
        private int quantity;

        @NotNull(message = "Price must not be null")
        private double price;
    }
}
