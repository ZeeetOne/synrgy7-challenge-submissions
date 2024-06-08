package com.example.binarfud.controller;

import com.example.binarfud.entity.Order;
import com.example.binarfud.entity.OrderDetail;
import com.example.binarfud.dto.OrderDetailDto;
import com.example.binarfud.dto.requests.OrderDetailRequestCreateDto;
import com.example.binarfud.dto.requests.OrderDetailRequestUpdateDto;
import com.example.binarfud.dto.responses.OrderDetailResponseDto;
import com.example.binarfud.service.OrderDetailService;
import com.example.binarfud.service.OrderService;
import com.example.binarfud.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/order-details")
@Tag(name = "Order Detail Controller", description = "Order Detail management APIs")
public class OrderDetailController {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private OrderDetailService orderDetailService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    @Operation(summary = "Save an order detail", description = "Save a new order detail")
    @PostMapping
    public ResponseEntity<Map<String, Object>> saveOrderDetail(@RequestBody OrderDetailRequestCreateDto orderDetailRequestCreateDto) {
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("status", "success");
        Map<String, Object> data = new HashMap<>();
        OrderDetail orderDetail = orderDetailService.saveOrderDetail(orderDetailRequestCreateDto);
        OrderDetailResponseDto orderDetailResponseDto = modelMapper.map(orderDetail, OrderDetailResponseDto.class);
        data.put("orderDetail", orderDetailResponseDto);
        response.put("data", data);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Operation(summary = "Get order details by order", description = "Retrieve order details by order ID")
    @GetMapping("/order/{orderId}")
    public ResponseEntity<Map<String, Object>> getOrderDetailsByOrder(@PathVariable("orderId") UUID orderId) {
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("status", "success");
        Map<String, Object> data = new HashMap<>();
        List<OrderDetail> orderDetailList = orderDetailService.getOrderDetailsByOrder(orderService.getOrder(orderId));
        List<OrderDetailResponseDto> orderDetailResponseDtoList = orderDetailList.stream()
                .map(orderDetail -> modelMapper.map(orderDetail, OrderDetailResponseDto.class))
                .toList();
        data.put("orderDetail", orderDetailResponseDtoList);
        response.put("data", data);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/user/{userId}/order-details")
    public ResponseEntity<Map<String, Object>> getOrderDetailsByUser(@PathVariable("userId") UUID userId) {
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("status", "success");
        Map<String, Object> data = new HashMap<>();

        // Fetch orders by user
        List<Order> orderList = orderService.getOrdersByUser(userService.getUser(userId));

        // Create a list to hold all the order details for the user's orders
        List<OrderDetailResponseDto> allOrderDetails = new ArrayList<>();

        // Fetch order details for each order
        for (Order order : orderList) {
            List<OrderDetail> orderDetailList = orderDetailService.getOrderDetailsByOrder(order);
            List<OrderDetailResponseDto> orderDetailResponseDtoList = orderDetailList.stream()
                    .map(orderDetail -> modelMapper.map(orderDetail, OrderDetailResponseDto.class))
                    .toList();
            allOrderDetails.addAll(orderDetailResponseDtoList);
        }

        data.put("orderDetails", allOrderDetails);
        response.put("data", data);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "Update order detail", description = "Update an existing order detail")
    @PutMapping("/{orderDetailId}")
    public ResponseEntity<Map<String, Object>> updateOrderDetail(@PathVariable("orderDetailId") UUID orderDetailId, @RequestBody OrderDetailRequestUpdateDto orderDetailRequestUpdateDto) {
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("status", "success");
        Map<String, Object> data = new HashMap<>();
        OrderDetailDto updatedOrderDetail = orderDetailService.updateOrderDetail(orderDetailId, orderDetailRequestUpdateDto);
        data.put("orderDetails", updatedOrderDetail);
        response.put("data", data);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "Delete order detail", description = "Delete an order detail by its ID")
    @DeleteMapping("/{orderDetailId}")
    public ResponseEntity<Map<String, Object>> deleteOrderDetail(@PathVariable("orderDetailId") UUID orderDetailId) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        orderDetailService.deleteOrderDetail(orderDetailId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
