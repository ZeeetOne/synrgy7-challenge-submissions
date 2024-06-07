package com.example.binarfud.service;

import com.example.binarfud.model.Merchant;
import com.example.binarfud.model.Order;
import com.example.binarfud.model.OrderDetail;
import com.example.binarfud.model.Product;
import com.example.binarfud.payload.OrderDetailDto;
import com.example.binarfud.payload.ProductDto;
import com.example.binarfud.payload.requests.OrderDetailRequestCreateDto;
import com.example.binarfud.payload.requests.OrderDetailRequestUpdateDto;
import com.example.binarfud.payload.requests.ProductRequestCreateDto;
import com.example.binarfud.payload.requests.ProductRequestUpdateDto;

import java.util.List;
import java.util.UUID;

public interface OrderDetailService {
    OrderDetail saveOrderDetail(OrderDetailRequestCreateDto orderDetailRequestCreateDto);
    List<OrderDetail> getOrderDetailsByOrder(Order order);
    OrderDetailDto updateOrderDetail(UUID orderDetailId, OrderDetailRequestUpdateDto orderDetailRequestUpdateDto);
    void deleteOrderDetail(UUID orderDetailId);
}
