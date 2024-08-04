package com.binarfud.binarfud_service.dto.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserRequestDto {
    @NotBlank(message = "Username must not be blank")
    private String username;

    @NotBlank(message = "Email address must not be blank")
    @Email(message = "Email address should be valid")
    private String emailAddress;

    @NotBlank(message = "Password must not be blank")
    private String password;
}
