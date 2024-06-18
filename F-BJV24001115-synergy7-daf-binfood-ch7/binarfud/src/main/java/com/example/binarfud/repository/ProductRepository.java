package com.example.binarfud.repository;

import com.example.binarfud.model.Merchant;
import com.example.binarfud.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {
    List<Product> findByMerchant(Merchant merchant);
}
