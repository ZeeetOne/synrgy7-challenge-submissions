package com.example.binarfud.service;

import com.example.binarfud.entity.Order;
import com.example.binarfud.entity.OrderDetail;
import com.example.binarfud.dto.OrderDetailDto;
import com.example.binarfud.dto.requests.OrderDetailRequestCreateDto;
import com.example.binarfud.dto.requests.OrderDetailRequestUpdateDto;

import java.util.List;
import java.util.UUID;

public interface OrderDetailService {
    OrderDetail saveOrderDetail(OrderDetailRequestCreateDto orderDetailRequestCreateDto);
    List<OrderDetail> getOrderDetailsByOrder(Order order);
    OrderDetailDto updateOrderDetail(UUID orderDetailId, OrderDetailRequestUpdateDto orderDetailRequestUpdateDto);
    void deleteOrderDetail(UUID orderDetailId);
}
