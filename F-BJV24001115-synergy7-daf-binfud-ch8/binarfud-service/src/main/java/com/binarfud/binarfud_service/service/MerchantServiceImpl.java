package com.binarfud.binarfud_service.service;

import com.binarfud.binarfud_service.dto.MerchantDto;
import com.binarfud.binarfud_service.dto.requests.MerchantRequestCreateDto;
import com.binarfud.binarfud_service.dto.requests.MerchantRequestUpdateDto;
import com.binarfud.binarfud_service.entity.Merchant;
import com.binarfud.binarfud_service.repository.MerchantRepository;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class MerchantServiceImpl implements MerchantService {

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    MerchantRepository merchantRepository;

    @Override
    public Merchant saveMerchant(MerchantRequestCreateDto merchantRequestCreateDto) {
        Merchant merchant = new Merchant();
        merchant.setName(merchantRequestCreateDto.getMerchantName());
        merchant.setLocation(merchantRequestCreateDto.getMerchantLocation());
        merchantRepository.save(merchant);
        return modelMapper.map(merchant, Merchant.class);
    }

    @Override
    public List<Merchant> getMerchants() {
        return merchantRepository.findAll();
    }

    @Override
    public Merchant getMerchant(UUID merchantId) {
        return merchantRepository.findById(merchantId).orElseThrow(() -> new RuntimeException("Not Found"));
    }

    @Override
    public MerchantDto updateMerchant(UUID merchantId, MerchantRequestUpdateDto merchantRequestUpdateDto) {
        Merchant existingMerchant = merchantRepository.findById(merchantId).orElseThrow(() -> new RuntimeException("Not Found"));
        existingMerchant.setName(merchantRequestUpdateDto.getMerchantName());
        existingMerchant.setLocation(merchantRequestUpdateDto.getMerchantLocation());
        existingMerchant.setOpen(merchantRequestUpdateDto.isOpen());
        Merchant updatedMerchant = merchantRepository.save(existingMerchant);
        return modelMapper.map(updatedMerchant, MerchantDto.class);
    }

    @Override
    public Merchant changeMerchantStatus(UUID merchantId, boolean open) {
        Merchant merchant = merchantRepository.findById(merchantId)
                .orElseThrow(() -> new EntityNotFoundException("Merchant not found with ID: " + merchantId));
        merchant.setOpen(open);
        return merchantRepository.save(merchant);
    }

    @Override
    public void deleteMerchant(UUID merchantId) {
        Merchant existingMerchant = merchantRepository.findById(merchantId).orElseThrow(() -> new RuntimeException("Not Found"));
        existingMerchant.setDeleted(true);
        merchantRepository.save(existingMerchant);
    }
}
