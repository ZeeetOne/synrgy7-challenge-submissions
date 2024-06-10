package com.example.binarfud.payload.requests;

import lombok.Data;

@Data
public class MerchantRequestUpdateDto {
    private String merchantName;
    private String merchantLocation;
    private boolean open;
}
