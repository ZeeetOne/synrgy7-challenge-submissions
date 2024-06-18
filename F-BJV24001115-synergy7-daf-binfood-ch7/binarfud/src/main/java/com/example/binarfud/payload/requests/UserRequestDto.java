package com.example.binarfud.payload.requests;

import lombok.Data;

@Data
public class UserRequestDto {
    private String username;
    private String emailAddress;
    private String password;
}
