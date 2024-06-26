package com.binarfud.binarfud_service.controller;

import com.binarfud.binarfud_service.dto.MerchantDto;
import com.binarfud.binarfud_service.dto.requests.MerchantRequestCreateDto;
import com.binarfud.binarfud_service.dto.requests.MerchantRequestUpdateDto;
import com.binarfud.binarfud_service.dto.requests.MerchantStatusUpdateDto;
import com.binarfud.binarfud_service.entity.Merchant;
import com.binarfud.binarfud_service.service.MerchantService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/merchants")
@Tag(name = "Merchant Controller", description = "Merchant management APIs")
public class MerchantController {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private MerchantService merchantService;

    @Operation(summary = "Create a new merchant", description = "Create a new merchant with the provided details")
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("create")
    public ResponseEntity<Map<String, Object>> saveMerchant(@RequestBody MerchantRequestCreateDto merchantRequestCreateDto) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        Map<String, Object> data = new HashMap<>();
        data.put("merchant", merchantService.saveMerchant(merchantRequestCreateDto));
        response.put("data", data);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Operation(summary = "Get all merchants", description = "Retrieve all merchants")
    @GetMapping
    public ResponseEntity<List<Merchant>> getMerchants() {
        return new ResponseEntity<>(merchantService.getMerchants(), HttpStatus.OK);
    }

    @Operation(summary = "Get a merchant by ID", description = "Retrieve a merchant by its ID")
    @GetMapping("/{merchantId}")
    public ResponseEntity<Merchant> getMerchant(@PathVariable("merchantId") UUID merchantId) {
        Merchant merchant = merchantService.getMerchant(merchantId);
        return new ResponseEntity<>(merchant, HttpStatus.OK);
    }

    @Operation(summary = "Update an existing merchant", description = "Update the details of an existing merchant")
    @PreAuthorize("hasRole('MERCHANT')")
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

    @Operation(summary = "Change merchant status", description = "Change the status of an existing merchant (open or closed)")
    @PreAuthorize("hasRole('MERCHANT')")
    @PutMapping("/{merchantId}/status")
    public ResponseEntity<Map<String, Object>> changeMerchantStatus(@PathVariable UUID merchantId, @RequestBody MerchantStatusUpdateDto merchantStatusUpdateDto) {
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("status", "success");
        Map<String, Object> data = new HashMap<>();
        data.put("merchant", merchantService.changeMerchantStatus(merchantId, merchantStatusUpdateDto.isOpen()));
        response.put("data", data);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "Delete a merchant", description = "Delete an existing merchant by its ID")
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{merchantId}")
    public ResponseEntity<Map<String, Object>> deleteMerchant(@PathVariable("merchantId") UUID merchantId) {
        merchantService.deleteMerchant(merchantId);
        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
