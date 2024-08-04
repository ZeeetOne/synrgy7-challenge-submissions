package com.binarfud.binarfud_service.repository;

import com.binarfud.binarfud_service.entity.InitializationStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface InitializationStatusRepository extends JpaRepository<InitializationStatus, UUID> {
    boolean existsByInitialized(boolean initialized);
}
