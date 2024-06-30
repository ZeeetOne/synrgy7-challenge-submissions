package com.binarfud.binarfud_service.repository;

import com.binarfud.binarfud_service.entity.accounts.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByUsername(String username);
    Boolean existsByUsername(String username);
    Boolean existsByEmailAddress(String emailAddress);

    @Query("SELECT DISTINCT r.name FROM User u JOIN u.roles r WHERE u.emailAddress = :email")
    List<String> findRolesByEmail(String email);

    Optional<User> findByEmailAddress(String emailAddress);
}
