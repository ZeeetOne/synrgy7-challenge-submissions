package com.binarfud.binarfud_service.dto.requests;

import lombok.Data;

@Data
public class LoginRequestDto {
    private String username;
    private String password;
}
