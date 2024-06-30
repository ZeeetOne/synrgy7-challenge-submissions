package com.binarfud.binarfud_service.repository;

import com.binarfud.binarfud_service.entity.accounts.ERole;
import com.binarfud.binarfud_service.entity.accounts.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RoleRepository extends JpaRepository<Role, UUID> {
    Role findByName(ERole name);
    boolean existsByName(ERole eRole);
}
