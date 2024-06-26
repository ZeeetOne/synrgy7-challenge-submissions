package com.binarfud.binarfud_service.repository;

import com.binarfud.binarfud_service.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface TokenRepository extends JpaRepository<Token, UUID> {
    void deleteByToken(String token);
}
