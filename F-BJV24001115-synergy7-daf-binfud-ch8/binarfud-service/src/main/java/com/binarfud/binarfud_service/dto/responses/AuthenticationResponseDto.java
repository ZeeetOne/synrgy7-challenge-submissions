package com.binarfud.binarfud_service.dto.responses;

import lombok.Data;
import lombok.NonNull;

import java.util.List;

@Data
public class AuthenticationResponseDto {
    @NonNull
    private String token;

    @NonNull
    private String type = "Bearer";

    @NonNull
    private String username;

    @NonNull
    private List<String> roles;

    public AuthenticationResponseDto(String token, String username, List<String> roles) {
        this.token = token;
        this.username = username;
        this.roles = roles;
    }
}
