package com.example.binarfud.controller;

import com.example.binarfud.model.Merchant;
import com.example.binarfud.model.Order;
import com.example.binarfud.payload.MerchantDto;
import com.example.binarfud.payload.requests.MerchantRequestCreateDto;
import com.example.binarfud.payload.requests.MerchantRequestUpdateDto;
import com.example.binarfud.payload.requests.MerchantStatusUpdateDto;
import com.example.binarfud.payload.requests.OrderStatusUpdateDto;
import com.example.binarfud.payload.responses.OrderResponseDto;
import com.example.binarfud.service.MerchantService;
import io.swagger.v3.oas.annotations.Operation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/merchants")
public class MerchantController {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private MerchantService merchantService;

    // insert a merchant
    @PostMapping
    public ResponseEntity<Map<String, Object>> saveMerchant(@RequestBody MerchantRequestCreateDto merchantRequestCreateDto) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        Map<String, Object> data = new HashMap<>();
        data.put("merchant", merchantService.saveMerchant(merchantRequestCreateDto));
        response.put("data", data);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // get all the merchants
    @GetMapping
    public ResponseEntity<List<Merchant>> getMerchants() {
        return new ResponseEntity<>(merchantService.getMerchants(), HttpStatus.OK);
    }

    // get a merchant by id
    @GetMapping("/{merchantId}")
    public ResponseEntity<Merchant> getMerchant(@PathVariable("merchantId") UUID merchantId) {
        Merchant merchant = merchantService.getMerchant(merchantId);
        return new ResponseEntity<>(merchant, HttpStatus.OK);
    }

    // update an existing merchant
    @PutMapping("/{merchantId}")
    public ResponseEntity<Map<String, Object>> updateMerchant(@PathVariable("merchantId") UUID merchantId, @RequestBody MerchantRequestUpdateDto merchantRequestUpdateDto) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        Map<String, Object> data = new HashMap<>();
        MerchantDto updatedMerchant = merchantService.updateMerchant(merchantId, merchantRequestUpdateDto);
        data.put("merchant", updatedMerchant);
        response.put("data", data);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{merchantId}/status")
    public ResponseEntity<Map<String, Object>> changeMerchantStatus(@PathVariable UUID merchantId, @RequestBody MerchantStatusUpdateDto merchantStatusUpdateDto) {
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("status", "success");
        Map<String, Object> data = new HashMap<>();
        data.put("merchant", merchantService.changeMerchantStatus(merchantId, merchantStatusUpdateDto.isOpen()));
        response.put("data", data);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // delete an existing merchant
    @DeleteMapping("/{merchantId}")
    public ResponseEntity<Map<String, Object>> deleteMerchant(@PathVariable("merchantId") UUID merchantId) {
        merchantService.deleteMerchant(merchantId);
        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
