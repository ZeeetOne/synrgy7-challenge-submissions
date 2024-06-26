package com.binarfud.binarfud_service.controller;

import com.binarfud.binarfud_service.dto.OrderDto;
import com.binarfud.binarfud_service.dto.requests.OrderRequestCreateDto;
import com.binarfud.binarfud_service.dto.requests.OrderRequestUpdateDto;
import com.binarfud.binarfud_service.dto.requests.OrderStatusUpdateDto;
import com.binarfud.binarfud_service.dto.responses.OrderResponseDto;
import com.binarfud.binarfud_service.entity.Order;
import com.binarfud.binarfud_service.service.OrderService;
import com.binarfud.binarfud_service.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.*;

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

    @Operation(summary = "Save an order", description = "Save a new order")
    @PreAuthorize("hasRole('USER') and principal.isVerified()")
    @PostMapping
    public ResponseEntity<Map<String, Object>> saveOrder(@RequestBody OrderRequestCreateDto orderRequestCreateDto) {
        Map<String, Object> response = new LinkedHashMap<>();
        response.put(STATUS, SUCCESS);
        Map<String, Object> data = new HashMap<>();
        Order order = orderService.saveOrder(orderRequestCreateDto);
        OrderResponseDto orderResponseDto = modelMapper.map(order, OrderResponseDto.class);
        data.put(ORDER, orderResponseDto);
        response.put("data", data);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Operation(summary = "Get orders by user", description = "Retrieve orders by user ID")
    @PreAuthorize("hasRole('USER') and principal.isVerified()")
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
    @PreAuthorize("hasRole('USER') and principal.isVerified()")
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
    @PreAuthorize("hasRole('MERCHANT') and principal.isVerified()")
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
