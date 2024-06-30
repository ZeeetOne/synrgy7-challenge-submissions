package com.binarfud.binarfud_service.dto.responses;

import lombok.Data;

import java.util.List;

@Data
public class AuthenticationResponseDto {
    private String token;
    private String type = "Bearer";
    private String username;
    private List<String> roles;

    public AuthenticationResponseDto(String token, String username, List<String> roles) {
        this.token = token;
        this.username = username;
        this.roles = roles;
    }
}
