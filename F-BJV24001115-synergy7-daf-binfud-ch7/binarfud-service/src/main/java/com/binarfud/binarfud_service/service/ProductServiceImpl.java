package com.binarfud.binarfud_service.service;

import com.binarfud.binarfud_service.dto.ProductDto;
import com.binarfud.binarfud_service.dto.requests.ProductRequestCreateDto;
import com.binarfud.binarfud_service.dto.requests.ProductRequestUpdateDto;
import com.binarfud.binarfud_service.entity.Merchant;
import com.binarfud.binarfud_service.entity.Product;
import com.binarfud.binarfud_service.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    MerchantService merchantService;

    @Override
    public Product saveProduct(ProductRequestCreateDto productRequestCreateDto) {
        Product product = new Product();
        product.setName(productRequestCreateDto.getProductName());
        product.setPrice(productRequestCreateDto.getPrice());
        product.setMerchant(merchantService.getMerchant(productRequestCreateDto.getMerchantId()));
        product.setCategory(productRequestCreateDto.getCategory());
        productRepository.save(product);
        return modelMapper.map(product, Product.class);
    }

    @Override
    public List<Product> getProducts(Merchant merchant) {
        return productRepository.findByMerchant(merchant).stream()
                .map(product -> modelMapper.map(product, Product.class))
                .toList();
    }

    @Override
    public ProductDto updateProduct(UUID productId, ProductRequestUpdateDto productRequestUpdateDto) {
        Product existingProduct = productRepository.findById(productId).orElseThrow(() -> new RuntimeException("Not Found"));
        existingProduct.setName(productRequestUpdateDto.getProductName());
        existingProduct.setPrice(productRequestUpdateDto.getPrice());
        existingProduct.setCategory(productRequestUpdateDto.getCategory());
        Product updatedProduct = productRepository.save(existingProduct);
        return modelMapper.map(updatedProduct, ProductDto.class);
    }

    @Override
    public void deleteProduct(UUID productId) {
        Product existingProduct = productRepository.findById(productId).orElseThrow(() -> new RuntimeException("Not Found"));
        existingProduct.setDeleted(true);
        productRepository.save(existingProduct);
    }

    @Override
    public Product getProduct(UUID productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Product not found with ID: " + productId));
    }
}
