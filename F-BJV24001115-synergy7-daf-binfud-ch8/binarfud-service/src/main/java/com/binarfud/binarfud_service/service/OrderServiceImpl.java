package com.binarfud.binarfud_service.service;

import com.binarfud.binarfud_service.dto.OrderDto;
import com.binarfud.binarfud_service.dto.requests.OrderRequestCreateDto;
import com.binarfud.binarfud_service.dto.requests.OrderRequestUpdateDto;
import com.binarfud.binarfud_service.dto.OrderCompletedEventDto;
import com.binarfud.binarfud_service.entity.Order;
import com.binarfud.binarfud_service.entity.accounts.User;
import com.binarfud.binarfud_service.repository.OrderRepository;
import com.binarfud.binarfud_service.repository.ProductRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    UserService userService;

    @Autowired
    MessageProducer messageProducer;

    private static final String ORDERNOTFOUNDWITHID = "Order not found with ID: ";

    /* @Override
    public Order saveOrder(OrderRequestCreateDto orderRequestCreateDto) {
        Order order = new Order();
        order.setUser(userService.getUser(orderRequestCreateDto.getUserId()));
        order.setOrderTime(LocalDateTime.now());
        order.setDestinationAddress(orderRequestCreateDto.getDestinationAddress());
        orderRepository.save(order);
        return modelMapper.map(order, Order.class);
    } */

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
        order = orderRepository.save(order);

        if (completed) {
            messageProducer.sendOrderCompletedEvent(order);
        }

        return order;
    }

    @Override
    public void deleteOrder(UUID orderId) {
        Order existingOrder = orderRepository.findById(orderId).
                orElseThrow(() -> new EntityNotFoundException(ORDERNOTFOUNDWITHID + orderId));
        existingOrder.setDeleted(true);
        orderRepository.save(existingOrder);
    }

    @Override
    @Transactional
    public Order saveOrder(Order order) {
        return orderRepository.save(order);
    }


}
