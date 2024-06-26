package com.binarfud.binarfud_service.dto.requests;

import lombok.Data;

@Data
public class SignupRequestDto {
    private String username;
    private String password;
    private String email;
}
