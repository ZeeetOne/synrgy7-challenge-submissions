package com.binarfud.binarfud_service.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class UserDto {
    @NotNull(message = "ID must not be null")
    private UUID id;

    @NotBlank(message = "Username must not be blank")
    private String username;

    @NotBlank(message = "Email address must not be blank")
    @Email(message = "Email address should be valid")
    private String emailAddress;

    @NotBlank(message = "Password must not be blank")
    private String password;
}
