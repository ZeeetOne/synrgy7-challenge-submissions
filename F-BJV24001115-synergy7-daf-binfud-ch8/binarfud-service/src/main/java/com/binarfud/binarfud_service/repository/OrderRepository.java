package com.binarfud.binarfud_service.repository;

import com.binarfud.binarfud_service.entity.Order;
import com.binarfud.binarfud_service.entity.accounts.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<Order, UUID> {
    List<Order> findByUser(User user);
}
