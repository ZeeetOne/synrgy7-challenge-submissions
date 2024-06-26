package com.example.binarfud.controller;

import com.example.binarfud.model.Order;
import com.example.binarfud.payload.OrderDto;
import com.example.binarfud.payload.requests.OrderRequestUpdateDto;
import com.example.binarfud.payload.requests.OrderStatusUpdateDto;
import com.example.binarfud.payload.requests.OrderRequestCreateDto;
import com.example.binarfud.payload.responses.OrderResponseDto;
import com.example.binarfud.service.OrderService;
import com.example.binarfud.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/orders")
@Tag(name = "Order Controller", description = "Order management APIs")
public class OrderController {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    private static final String ORDER = "order";
    private static final String STATUS = "status";
    private static final String SUCCESS = "success";

    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

    @Operation(summary = "Save an order", description = "Save a new order")
    @PostMapping
    public ResponseEntity<Map<String, Object>> saveOrder(@RequestBody OrderRequestCreateDto orderRequestCreateDto) {
        logger.info("Request to save order: {}", orderRequestCreateDto);
        Map<String, Object> response = new LinkedHashMap<>();
        response.put(STATUS, SUCCESS);
        Map<String, Object> data = new HashMap<>();
        Order order = orderService.saveOrder(orderRequestCreateDto);
        OrderResponseDto orderResponseDto = modelMapper.map(order, OrderResponseDto.class);
        data.put(ORDER, orderResponseDto);
        response.put("data", data);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllOrders(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<Order> ordersPage = orderService.getAllOrders(PageRequest.of(page, size));
        List<OrderResponseDto> orderResponseDtoList = ordersPage.getContent().stream()
                .map(order -> modelMapper.map(order, OrderResponseDto.class))
                .collect(Collectors.toList());

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("status", "success");
        response.put("data", orderResponseDtoList);
        response.put("currentPage", ordersPage.getNumber());
        response.put("totalItems", ordersPage.getTotalElements());
        response.put("totalPages", ordersPage.getTotalPages());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "Get orders by user", description = "Retrieve orders by user ID")
    @GetMapping("/user/{userId}")
    public ResponseEntity<Map<String, Object>> getOrdersByUser(@PathVariable("userId") UUID userId) {
        Map<String, Object> response = new LinkedHashMap<>();
        response.put(STATUS, SUCCESS);
        Map<String, Object> data = new HashMap<>();
        List<Order> orderList = orderService.getOrdersByUser(userService.getUser(userId));
        List<OrderResponseDto> orderResponseDtoList = orderList.stream()
                .map(order -> modelMapper.map(order, OrderResponseDto.class))
                .toList();
        data.put(ORDER, orderResponseDtoList);
        response.put("data", data);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "Get order by ID", description = "Retrieve an order by its ID")
    @GetMapping("/{orderId}")
    public ResponseEntity<Map<String, Object>> getOrder(@PathVariable("orderId") UUID orderId) {
        logger.info("Request to get order with ID: {}", orderId);
        Map<String, Object> response = new LinkedHashMap<>();
        response.put(STATUS, SUCCESS);
        Map<String, Object> data = new HashMap<>();
        Order order = orderService.getOrder(orderId);
        OrderResponseDto orderResponseDto = modelMapper.map(order, OrderResponseDto.class);
        data.put(ORDER, orderResponseDto);
        response.put("data", data);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "Update order", description = "Update an existing order")
    @PutMapping("/{orderId}")
    public ResponseEntity<Map<String, Object>> updateOrder(@PathVariable("orderId") UUID orderId, @RequestBody OrderRequestUpdateDto orderRequestUpdateDto) {
        Map<String, Object> response = new LinkedHashMap<>();
        response.put(STATUS, SUCCESS);
        Map<String, Object> data = new HashMap<>();
        OrderDto updatedOrder = orderService.updateOrder(orderId, orderRequestUpdateDto);
        data.put(ORDER, updatedOrder);
        response.put("data", data);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "Confirm order completion", description = "Confirm completion of an order")
    @PutMapping("/{orderId}/confirm")
    public ResponseEntity<Map<String, Object>> confirmOrderCompletion(@PathVariable UUID orderId, @RequestBody OrderStatusUpdateDto orderStatusUpdateDto) {
        Map<String, Object> response = new LinkedHashMap<>();
        response.put(STATUS, SUCCESS);
        Map<String, Object> data = new HashMap<>();
        Order order = orderService.confirmOrderCompletion(orderId, orderStatusUpdateDto.isCompleted());
        OrderResponseDto orderResponseDto = modelMapper.map(order, OrderResponseDto.class);
        data.put(ORDER, orderResponseDto);
        response.put("data", data);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "Delete order", description = "Delete an order by its ID")
    @DeleteMapping("/{orderId}")
    public ResponseEntity<Map<String, Object>> deleteOrder(@PathVariable("orderId") UUID orderId) {
        Map<String, Object> response = new HashMap<>();
        response.put(STATUS, SUCCESS);
        orderService.deleteOrder(orderId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
