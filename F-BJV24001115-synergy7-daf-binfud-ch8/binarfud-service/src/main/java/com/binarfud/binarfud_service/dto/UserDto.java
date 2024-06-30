package com.binarfud.binarfud_service.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class UserDto {
    private UUID id;
    private String username;
    private String emailAddress;
    private String password;
}
