package com.example.binarfud.dto.requests;

import lombok.Data;

@Data
public class MerchantRequestUpdateDto {
    private String merchantName;
    private String merchantLocation;
    private boolean open;
}
