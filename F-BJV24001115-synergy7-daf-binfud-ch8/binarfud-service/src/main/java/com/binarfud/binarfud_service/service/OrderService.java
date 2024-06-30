package com.binarfud.binarfud_service.service;

import com.binarfud.binarfud_service.dto.OrderDto;
import com.binarfud.binarfud_service.dto.requests.OrderRequestCreateDto;
import com.binarfud.binarfud_service.dto.requests.OrderRequestUpdateDto;
import com.binarfud.binarfud_service.entity.Order;
import com.binarfud.binarfud_service.entity.accounts.User;

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
