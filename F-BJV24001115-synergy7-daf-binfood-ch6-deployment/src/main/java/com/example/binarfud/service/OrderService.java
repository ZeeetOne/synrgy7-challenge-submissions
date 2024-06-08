package com.example.binarfud.service;

import com.example.binarfud.entity.Order;
import com.example.binarfud.entity.accounts.User;
import com.example.binarfud.dto.OrderDto;
import com.example.binarfud.dto.requests.OrderRequestCreateDto;
import com.example.binarfud.dto.requests.OrderRequestUpdateDto;

import java.util.List;
import java.util.UUID;

public interface OrderService {

    Order saveOrder(OrderRequestCreateDto orderRequestCreateDto);
    List<Order> getOrdersByUser(User user);
    Order getOrder(UUID orderId);
    OrderDto updateOrder(UUID orderId, OrderRequestUpdateDto orderRequestUpdateDto);
    Order confirmOrderCompletion(UUID orderId, boolean completed);
    void deleteOrder(UUID orderId);
}
