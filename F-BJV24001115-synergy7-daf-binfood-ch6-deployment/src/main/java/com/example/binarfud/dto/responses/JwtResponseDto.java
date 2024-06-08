package com.example.binarfud.dto.responses;

import lombok.Data;

import java.util.List;

@Data
public class JwtResponseDto {
    private String token;
    private String type = "Bearer";
    private String username;
    private List<String> roles;

    public JwtResponseDto(String token, String username, List<String> roles) {
        this.token = token;
        this.username = username;
        this.roles = roles;
    }
}
