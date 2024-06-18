package com.example.binarfud.service;

import com.example.binarfud.model.Order;
import com.example.binarfud.model.OrderDetail;
import com.example.binarfud.model.Product;
import com.example.binarfud.payload.OrderDetailDto;
import com.example.binarfud.payload.OrderDto;
import com.example.binarfud.payload.requests.OrderDetailRequestCreateDto;
import com.example.binarfud.payload.requests.OrderDetailRequestUpdateDto;
import com.example.binarfud.repository.OrderDetailRepository;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class OrderDetailServiceImpl implements OrderDetailService {

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    OrderDetailRepository orderDetailRepository;

    @Autowired
    OrderService orderService;

    @Autowired
    ProductService productService;

    @Override
    public OrderDetail saveOrderDetail(OrderDetailRequestCreateDto orderDetailRequestCreateDto) {
        Order order = orderService.getOrder(orderDetailRequestCreateDto.getOrderId());
        Product product = productService.getProduct(orderDetailRequestCreateDto.getProductId());
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setOrder(order);
        orderDetail.setProduct(product);
        orderDetail.setQuantity(orderDetailRequestCreateDto.getQuantity());
        orderDetail.setTotalPrice(calculateTotalPrice(orderDetailRequestCreateDto.getQuantity(), product.getPrice()));
        orderDetailRepository.save(orderDetail);
        return modelMapper.map(orderDetail, OrderDetail.class);
    }

    @Override
    public List<OrderDetail> getOrderDetailsByOrder(Order order) {
        return orderDetailRepository.findByOrder(order).stream()
                .map(orderDetail -> modelMapper.map(orderDetail, OrderDetail.class))
                .toList();
    }

    @Override
    public OrderDetailDto updateOrderDetail(UUID orderDetailId, OrderDetailRequestUpdateDto orderDetailRequestUpdateDto) {
        Product product = productService.getProduct(orderDetailRequestUpdateDto.getProductId());
        OrderDetail existingOrderDetail = orderDetailRepository.findById(orderDetailId).orElseThrow(() -> new EntityNotFoundException("Order not found with ID: " + orderDetailId));
        existingOrderDetail.setProduct(product);
        existingOrderDetail.setQuantity(orderDetailRequestUpdateDto.getQuantity());
        existingOrderDetail.setTotalPrice(calculateTotalPrice(orderDetailRequestUpdateDto.getQuantity(), product.getPrice()));
        OrderDetail updatedOrderDetail = orderDetailRepository.save(existingOrderDetail);
        return modelMapper.map(updatedOrderDetail, OrderDetailDto.class);
    }

    @Override
    public void deleteOrderDetail(UUID orderDetailId) {
        OrderDetail existingOrderDetail = orderDetailRepository.findById(orderDetailId).
                orElseThrow(() -> new EntityNotFoundException("Order not found with ID: " + orderDetailId));
        existingOrderDetail.setDeleted(true);
        orderDetailRepository.save(existingOrderDetail);
    }

    private Long calculateTotalPrice(int quantity, Long price) {
        return quantity * price;
    }
}
