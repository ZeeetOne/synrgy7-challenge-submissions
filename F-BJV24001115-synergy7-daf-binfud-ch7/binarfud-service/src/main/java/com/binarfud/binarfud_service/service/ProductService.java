package com.binarfud.binarfud_service.service;

import com.binarfud.binarfud_service.dto.ProductDto;
import com.binarfud.binarfud_service.dto.requests.ProductRequestCreateDto;
import com.binarfud.binarfud_service.dto.requests.ProductRequestUpdateDto;
import com.binarfud.binarfud_service.entity.Merchant;
import com.binarfud.binarfud_service.entity.Product;

import java.util.List;
import java.util.UUID;

public interface ProductService {
    Product saveProduct(ProductRequestCreateDto productRequestCreateDto);
    List<Product> getProducts(Merchant merchant);
    ProductDto updateProduct(UUID productId, ProductRequestUpdateDto productRequestUpdateDto);
    void deleteProduct(UUID productId);

    Product getProduct(UUID productId);
}
