package com.binarfud.binarfud_service.repository;

import com.binarfud.binarfud_service.entity.Merchant;
import com.binarfud.binarfud_service.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {
    List<Product> findByMerchant(Merchant merchant);
}
