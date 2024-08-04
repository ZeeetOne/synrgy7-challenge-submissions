package com.binarfud.binarfud_service.dto.responses;

import lombok.Data;
import lombok.NonNull;

@Data
public class UserResponseDto {
    @NonNull
    private String username;

    @NonNull
    private String emailAddress;
}
