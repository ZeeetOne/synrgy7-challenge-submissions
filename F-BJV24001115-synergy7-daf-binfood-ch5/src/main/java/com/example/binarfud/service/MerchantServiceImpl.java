package com.example.binarfud.service;

import com.example.binarfud.model.Merchant;
import com.example.binarfud.payload.MerchantDto;
import com.example.binarfud.payload.requests.MerchantRequestCreateDto;
import com.example.binarfud.payload.requests.MerchantRequestUpdateDto;
import com.example.binarfud.repository.MerchantRepository;
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
    public Merchant saveMerchant(MerchantRequestCreateDto merchantRequestDto) {
        Merchant merchant = new Merchant();
        merchant.setMerchantName(merchantRequestDto.getMerchantName());
        merchant.setMerchantLocation(merchantRequestDto.getMerchantLocation());
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
        existingMerchant.setMerchantName(merchantRequestUpdateDto.getMerchantName());
        existingMerchant.setMerchantLocation(merchantRequestUpdateDto.getMerchantLocation());
        existingMerchant.setOpen(merchantRequestUpdateDto.isOpen());
        Merchant updatedMerchant = merchantRepository.save(existingMerchant);
        return modelMapper.map(updatedMerchant, MerchantDto.class);
    }

    @Override
    public void deleteMerchant(UUID merchantId) {
        Merchant existingMerchant = merchantRepository.findById(merchantId).orElseThrow(() -> new RuntimeException("Not Found"));
        existingMerchant.setDeleted(true);
        merchantRepository.save(existingMerchant);
    }
}
