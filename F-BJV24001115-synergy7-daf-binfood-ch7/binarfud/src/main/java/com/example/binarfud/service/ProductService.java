package com.example.binarfud.service;

import com.example.binarfud.model.Merchant;
import com.example.binarfud.model.Product;
import com.example.binarfud.payload.ProductDto;
import com.example.binarfud.payload.requests.ProductRequestCreateDto;
import com.example.binarfud.payload.requests.ProductRequestUpdateDto;

import java.util.List;
import java.util.UUID;

public interface ProductService {
    Product saveProduct(ProductRequestCreateDto productRequestCreateDto);
    List<Product> getProducts(Merchant merchant);
    ProductDto updateProduct(UUID productId, ProductRequestUpdateDto productRequestUpdateDto);
    void deleteProduct(UUID productId);

    Product getProduct(UUID productId);
}
