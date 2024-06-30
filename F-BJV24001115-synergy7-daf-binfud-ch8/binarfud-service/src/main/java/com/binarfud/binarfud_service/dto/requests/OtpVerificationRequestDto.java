package com.binarfud.binarfud_service.dto.requests;

import lombok.Data;

@Data
public class OtpVerificationRequestDto {
    private String email;
    private String otp;
}
