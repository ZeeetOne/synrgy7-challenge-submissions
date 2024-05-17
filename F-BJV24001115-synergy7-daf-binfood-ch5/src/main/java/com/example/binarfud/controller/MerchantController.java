package com.example.binarfud.controller;

import com.example.binarfud.model.Merchant;
import com.example.binarfud.payload.MerchantDto;
import com.example.binarfud.payload.requests.MerchantRequestCreateDto;
import com.example.binarfud.payload.requests.MerchantRequestUpdateDto;
import com.example.binarfud.service.MerchantService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("merchant")
public class MerchantController {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private MerchantService merchantService;

    // insert a merchant
    @PostMapping
    public ResponseEntity<Map<String, Object>> saveMerchant(@RequestBody MerchantRequestCreateDto merchantRequestDto) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        Map<String, Object> data = new HashMap<>();
        data.put("merchant", merchantService.saveMerchant(merchantRequestDto));
        response.put("data", data);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // get all the merchants
    @GetMapping
    public ResponseEntity<List<Merchant>> getMerchants() {
        return new ResponseEntity<>(merchantService.getMerchants(), HttpStatus.OK);
    }

    // get a merchant by id
    @GetMapping("/{merchant_id}")
    public ResponseEntity<Merchant> getMerchant(@PathVariable("merchant_id") UUID merchantId) {
        Merchant merchant = merchantService.getMerchant(merchantId);
        return new ResponseEntity<>(merchant, HttpStatus.OK);
    }

    // update an existing merchant
    @PutMapping("/{merchant_id}")
    public ResponseEntity<Map<String, Object>> updateMerchant(@PathVariable("merchant_id") UUID merchantId, @RequestBody MerchantRequestUpdateDto merchantRequestUpdateDto) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        Map<String, Object> data = new HashMap<>();
        MerchantDto updatedMerchant = merchantService.updateMerchant(merchantId, merchantRequestUpdateDto);
        data.put("merchant", updatedMerchant);
        response.put("data", data);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // delete an existing merchant
    @DeleteMapping("/{merchant_id}")
    public ResponseEntity<Map<String, Object>> deleteMerchant(@PathVariable("merchant_id") UUID merchantId) {
        merchantService.deleteMerchant(merchantId);
        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
