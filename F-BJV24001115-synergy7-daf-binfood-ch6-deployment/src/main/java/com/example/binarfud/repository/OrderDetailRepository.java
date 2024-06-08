package com.example.binarfud.repository;

import com.example.binarfud.entity.Order;
import com.example.binarfud.entity.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, UUID> {
    List<Object> findByOrder(Order order);
}
