package com.binarfud.binarfud_service.service;

import com.binarfud.binarfud_service.dto.OrderDetailDto;
import com.binarfud.binarfud_service.dto.requests.OrderDetailRequestCreateDto;
import com.binarfud.binarfud_service.dto.requests.OrderDetailRequestUpdateDto;
import com.binarfud.binarfud_service.entity.Order;
import com.binarfud.binarfud_service.entity.OrderDetail;

import java.util.List;
import java.util.UUID;

public interface OrderDetailService {
    OrderDetail saveOrderDetail(OrderDetailRequestCreateDto orderDetailRequestCreateDto);
    List<OrderDetail> getOrderDetailsByOrder(Order order);
    OrderDetailDto updateOrderDetail(UUID orderDetailId, OrderDetailRequestUpdateDto orderDetailRequestUpdateDto);
    void deleteOrderDetail(UUID orderDetailId);
}
