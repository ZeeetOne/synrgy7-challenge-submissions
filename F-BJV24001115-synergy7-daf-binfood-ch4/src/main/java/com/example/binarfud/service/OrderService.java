package com.example.binarfud.service;

import com.example.binarfud.model.Order;
import com.example.binarfud.model.User;
import com.example.binarfud.payload.OrderDto;
import com.example.binarfud.payload.requests.OrderRequestCreateDto;
import com.example.binarfud.payload.requests.OrderRequestUpdateDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.UUID;

public interface OrderService {

    Order saveOrder(OrderRequestCreateDto orderRequestCreateDto);
    List<Order> getOrdersByUser(User user);
    Order getOrder(UUID orderId);
    OrderDto updateOrder(UUID orderId, OrderRequestUpdateDto orderRequestUpdateDto);
    Order confirmOrderCompletion(UUID orderId, boolean completed);
    void deleteOrder(UUID orderId);

    Page<Order> getAllOrders(PageRequest pageRequest);
}
