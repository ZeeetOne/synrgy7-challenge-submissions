package com.example.binarfud.payload.requests;

import lombok.Data;

@Data
public class MerchantRequestCreateDto {
    private String merchantName;
    private String merchantLocation;
}
