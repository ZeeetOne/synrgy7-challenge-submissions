package com.example.binarfud.repository;

import com.example.binarfud.entity.Order;
import com.example.binarfud.entity.accounts.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<Order, UUID> {
    List<Order> findByUser(User user);
}
