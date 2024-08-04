package com.binarfud.binarfud_service.service;

import com.binarfud.binarfud_service.dto.requests.OrderRequestCreateDtos;
import com.binarfud.binarfud_service.dto.responses.OrderResponseDtos;
import com.binarfud.binarfud_service.entity.Order;
import com.binarfud.binarfud_service.entity.OrderDetail;
import com.binarfud.binarfud_service.entity.Product;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderFacadeImpl implements OrderFacade {

    @Autowired
    OrderService orderService;
    @Autowired
    OrderDetailService orderDetailService;
    @Autowired
    UserService userService;
    @Autowired
    ProductService productService;
    @Autowired
    ModelMapper modelMapper;

    @Transactional
    @Override
    public OrderResponseDtos saveOrderWithDetails(OrderRequestCreateDtos orderDto) {
        // Create and save the order
        Order order = new Order();
        order.setUser(userService.getUser(orderDto.getUserId()));
        order.setOrderTime(LocalDateTime.now());
        order.setDestinationAddress(orderDto.getDestinationAddress());
        order.setOrderDetails(new ArrayList<>());

        // Create and save order details
        for (OrderRequestCreateDtos.OrderDetailRequestCreateDtos detailDto : orderDto.getOrderDetails()) {
            Product product = productService.getProduct(detailDto.getProductId());
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setOrder(order);
            orderDetail.setProduct(product);
            orderDetail.setQuantity(detailDto.getQuantity());
            orderDetail.setTotalPrice(calculateTotalPrice(detailDto.getQuantity(), product.getPrice()));
            order.getOrderDetails().add(orderDetail);
        }

        // Save the order (this will cascade and save order details as well)
        Order savedOrder = orderService.saveOrder(order);

        // Map to response DTO
        return mapToOrderResponseDto(savedOrder);
    }

    private Long calculateTotalPrice(int quantity, Long price) {
        return quantity * price;
    }

    private OrderResponseDtos mapToOrderResponseDto(Order order) {
        OrderResponseDtos responseDto = new OrderResponseDtos();
        responseDto.setOrderTime(order.getOrderTime());
        responseDto.setDestinationAddress(order.getDestinationAddress());
        responseDto.setCompleted(order.isCompleted());

        List<OrderResponseDtos.OrderDetailResponseCreateDtos> detailDtos = order.getOrderDetails().stream()
                .map(this::mapToOrderDetailResponseDto)
                .collect(Collectors.toList());
        responseDto.setOrderDetails(detailDtos);

        return responseDto;
    }

    private OrderResponseDtos.OrderDetailResponseCreateDtos mapToOrderDetailResponseDto(OrderDetail orderDetail) {
        OrderResponseDtos.OrderDetailResponseCreateDtos detailDto = new OrderResponseDtos.OrderDetailResponseCreateDtos();
        detailDto.setProductId(orderDetail.getProduct().getId());
        detailDto.setQuantity(orderDetail.getQuantity());
        detailDto.setPrice(orderDetail.getTotalPrice());
        return detailDto;
    }
}
