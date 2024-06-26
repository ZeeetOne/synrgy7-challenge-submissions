package com.binarfud.binarfud_service.dto.requests;

import lombok.Data;

@Data
public class UserRequestDto {
    private String username;
    private String emailAddress;
    private String password;
}
