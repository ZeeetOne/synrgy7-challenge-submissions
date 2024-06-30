package com.binarfud.binarfud_service.repository;

import com.binarfud.binarfud_service.entity.Order;
import com.binarfud.binarfud_service.entity.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, UUID> {
    List<Object> findByOrder(Order order);
}
