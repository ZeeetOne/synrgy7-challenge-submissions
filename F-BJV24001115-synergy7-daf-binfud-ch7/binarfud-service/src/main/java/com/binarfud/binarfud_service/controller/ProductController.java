package com.binarfud.binarfud_service.controller;

import com.binarfud.binarfud_service.dto.ProductDto;
import com.binarfud.binarfud_service.dto.requests.ProductRequestCreateDto;
import com.binarfud.binarfud_service.dto.requests.ProductRequestUpdateDto;
import com.binarfud.binarfud_service.dto.responses.ProductResponseDto;
import com.binarfud.binarfud_service.entity.Product;
import com.binarfud.binarfud_service.service.MerchantService;
import com.binarfud.binarfud_service.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/products")
@Tag(name = "Product Controller", description = "Product management APIs")
public class ProductController {

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    private ProductService productService;

    @Autowired
    private MerchantService merchantService;

    @Operation(summary = "Save a product", description = "Save a new product")
    @PreAuthorize("hasRole('MERCHANT')")
    @PostMapping
    public ResponseEntity<Map<String, Object>> saveProduct(@RequestBody ProductRequestCreateDto productRequestCreateDto) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        Map<String, Object> data = new HashMap<>();
        Product product = productService.saveProduct(productRequestCreateDto);
        ProductResponseDto productResponseDto = new ProductResponseDto();
        productResponseDto.setProductName(product.getName());
        productResponseDto.setPrice(product.getPrice());
        productResponseDto.setCategory(product.getCategory());
        data.put("product", productResponseDto);
        response.put("data", data);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Operation(summary = "Get products by merchant", description = "Retrieve products by merchant ID")
    @GetMapping("/merchant/{merchantId}")
    public ResponseEntity<Map<String, Object>> getProductsByMerchant(@PathVariable("merchantId") UUID merchantId) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        Map<String, Object> data = new HashMap<>();
        List<Product> productList = productService.getProducts(merchantService.getMerchant(merchantId));
        List<ProductResponseDto> productResponseDtoList = productList.stream()
                        .map(product -> modelMapper.map(product, ProductResponseDto.class))
                        .toList();
        data.put("product", productResponseDtoList);
        response.put("data", data);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "Get product by ID", description = "Retrieve a product by its ID")
    @GetMapping("/{productId}")
    public ResponseEntity<Product> getProduct(@PathVariable("productId") UUID productId) {
        Product product = productService.getProduct(productId);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @Operation(summary = "Update product", description = "Update an existing product")
    @PreAuthorize("hasRole('MERCHANT')")
    @PutMapping("/{productId}")
    public ResponseEntity<Map<String, Object>> updateProduct(@PathVariable("productId") UUID productId, @RequestBody ProductRequestUpdateDto productRequestUpdateDto) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        Map<String, Object> data = new HashMap<>();
        ProductDto updateProduct = productService.updateProduct(productId, productRequestUpdateDto);
        data.put("merchant", updateProduct);
        response.put("data", data);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "Delete product", description = "Delete an existing product")
    @PreAuthorize("hasRole('MERCHANT')")
    @DeleteMapping("/{productId}")
    public ResponseEntity<Map<String, Object>> deleteProduct(@PathVariable("productId") UUID productId) {
        productService.deleteProduct(productId);
        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
