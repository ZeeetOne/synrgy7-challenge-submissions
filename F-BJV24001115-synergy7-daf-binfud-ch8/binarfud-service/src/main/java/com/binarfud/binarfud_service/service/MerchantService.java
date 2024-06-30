package com.binarfud.binarfud_service.service;

import com.binarfud.binarfud_service.dto.MerchantDto;
import com.binarfud.binarfud_service.dto.requests.MerchantRequestCreateDto;
import com.binarfud.binarfud_service.dto.requests.MerchantRequestUpdateDto;
import com.binarfud.binarfud_service.entity.Merchant;

import java.util.List;
import java.util.UUID;

public interface MerchantService {
    Merchant saveMerchant(MerchantRequestCreateDto merchantRequestCreateDto);
    List<Merchant> getMerchants();
    Merchant getMerchant(UUID merchantId);
    MerchantDto updateMerchant(UUID merchantId, MerchantRequestUpdateDto merchantRequestUpdateDto);
    Merchant changeMerchantStatus(UUID merchantId, boolean open);
    void deleteMerchant(UUID merchantId);
}
