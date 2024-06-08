package com.example.binarfud.service;

import com.example.binarfud.entity.Merchant;
import com.example.binarfud.entity.Product;
import com.example.binarfud.dto.ProductDto;
import com.example.binarfud.dto.requests.ProductRequestCreateDto;
import com.example.binarfud.dto.requests.ProductRequestUpdateDto;

import java.util.List;
import java.util.UUID;

public interface ProductService {
    Product saveProduct(ProductRequestCreateDto productRequestCreateDto);
    List<Product> getProducts(Merchant merchant);
    ProductDto updateProduct(UUID productId, ProductRequestUpdateDto productRequestUpdateDto);
    void deleteProduct(UUID productId);

    Product getProduct(UUID productId);
}
