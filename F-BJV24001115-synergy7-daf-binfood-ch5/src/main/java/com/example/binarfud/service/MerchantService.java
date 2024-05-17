package com.example.binarfud.service;

import com.example.binarfud.model.Merchant;
import com.example.binarfud.payload.MerchantDto;
import com.example.binarfud.payload.requests.MerchantRequestCreateDto;
import com.example.binarfud.payload.requests.MerchantRequestUpdateDto;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface MerchantService {
    Merchant saveMerchant(MerchantRequestCreateDto merchantRequestDto);
    List<Merchant> getMerchants();
    Merchant getMerchant(UUID merchantId);
    MerchantDto updateMerchant(UUID merchantId, MerchantRequestUpdateDto merchantRequestUpdateDto);
    void deleteMerchant(UUID merchantId);
}
