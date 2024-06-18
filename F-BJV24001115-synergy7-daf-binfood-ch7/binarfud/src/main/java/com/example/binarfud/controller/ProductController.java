package com.example.binarfud.controller;

import com.example.binarfud.model.Merchant;
import com.example.binarfud.model.Product;
import com.example.binarfud.payload.ProductDto;
import com.example.binarfud.payload.requests.ProductRequestCreateDto;
import com.example.binarfud.payload.requests.ProductRequestUpdateDto;
import com.example.binarfud.payload.responses.ProductResponseDto;
import com.example.binarfud.service.MerchantService;
import com.example.binarfud.service.ProductService;
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
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    private ProductService productService;

    @Autowired
    private MerchantService merchantService;

    @PostMapping
    public ResponseEntity<Map<String, Object>> saveProduct(@RequestBody ProductRequestCreateDto productRequestCreateDto) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        Map<String, Object> data = new HashMap<>();
        Product product = productService.saveProduct(productRequestCreateDto);
        ProductResponseDto productResponseDto = new ProductResponseDto();
        productResponseDto.setProductName(product.getProductName());
        productResponseDto.setPrice(product.getPrice());
        productResponseDto.setCategory(product.getCategory());
        data.put("product", productResponseDto);
        response.put("data", data);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

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

    @GetMapping("/{productId}")
    public ResponseEntity<Product> getProduct(@PathVariable("productId") UUID productId) {
        Product product = productService.getProduct(productId);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

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

    @DeleteMapping("/{productId}")
    public ResponseEntity<Map<String, Object>> deleteProduct(@PathVariable("productId") UUID productId) {
        productService.deleteProduct(productId);
        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
