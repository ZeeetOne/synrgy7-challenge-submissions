package com.example.binarfud.service;

import com.example.binarfud.model.Order;
import com.example.binarfud.model.User;
import com.example.binarfud.payload.OrderDto;
import com.example.binarfud.payload.requests.OrderRequestCreateDto;
import com.example.binarfud.payload.requests.OrderRequestUpdateDto;
import com.example.binarfud.repository.OrderRepository;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    UserService userService;

    private static final String ORDERNOTFOUNDWITHID = "Order not found with ID: ";

    @Override
    public Order saveOrder(OrderRequestCreateDto orderRequestCreateDto) {
        Order order = new Order();
        order.setUser(userService.getUser(orderRequestCreateDto.getUser_id()));
        order.setOrderTime(LocalDateTime.now());
        order.setDestinationAddress(orderRequestCreateDto.getDestinationAddress());
        orderRepository.save(order);
        return modelMapper.map(order, Order.class);
    }

    @Override
    public List<Order> getOrdersByUser(User user) {
        return orderRepository.findByUser(user).stream()
                .map(order -> modelMapper.map(order, Order.class))
                .toList();
    }

    @Override
    public Order getOrder(UUID orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException(ORDERNOTFOUNDWITHID + orderId));
    }

    @Override
    public OrderDto updateOrder(UUID orderId, OrderRequestUpdateDto orderRequestUpdateDto) {
        Order existingOrder = orderRepository.findById(orderId).orElseThrow(() -> new EntityNotFoundException(ORDERNOTFOUNDWITHID + orderId));
        existingOrder.setDestinationAddress(orderRequestUpdateDto.getDestinationAddress());
        Order updatedOrder = orderRepository.save(existingOrder);
        return modelMapper.map(updatedOrder, OrderDto.class);
    }

    @Override
    public Order confirmOrderCompletion(UUID orderId, boolean completed) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException(ORDERNOTFOUNDWITHID + orderId));
        order.setCompleted(completed);
        return orderRepository.save(order);
    }

    @Override
    public void deleteOrder(UUID orderId) {
        Order existingOrder = orderRepository.findById(orderId).
                orElseThrow(() -> new EntityNotFoundException(ORDERNOTFOUNDWITHID + orderId));
        existingOrder.setDeleted(true);
        orderRepository.save(existingOrder);
    }

    @Override
    public Page<Order> getAllOrders(PageRequest pageRequest) {
        return orderRepository.findAll(pageRequest);
    }
}
