package com.binarfud.binarfud_service.dto.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PasswordResetRequestDto {
    @NotBlank(message = "Email must not be blank")
    @Email(message = "Email should be valid")
    private String email;
}
