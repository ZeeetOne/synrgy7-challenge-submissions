package com.binarfud.binarfud_service.controller;

import com.binarfud.binarfud_service.dto.OrderDetailDto;
import com.binarfud.binarfud_service.dto.requests.OrderDetailRequestCreateDto;
import com.binarfud.binarfud_service.dto.requests.OrderDetailRequestUpdateDto;
import com.binarfud.binarfud_service.dto.responses.OrderDetailResponseDto;
import com.binarfud.binarfud_service.entity.Order;
import com.binarfud.binarfud_service.entity.OrderDetail;
import com.binarfud.binarfud_service.service.OrderDetailService;
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
    @PreAuthorize("hasRole('USER')")
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
    @PreAuthorize("hasRole('USER')")
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

    @Operation(summary = "Get order details by user", description = "Retrieve order details by user ID")
    @GetMapping("/user/{userId}/order-details")
    public ResponseEntity<Map<String, Object>> getOrderDetailsByUser(@PathVariable("userId") UUID userId) {
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("status", "success");
        Map<String, Object> data = new HashMap<>();

        List<Order> orderList = orderService.getOrdersByUser(userService.getUser(userId));

        List<OrderDetailResponseDto> allOrderDetails = new ArrayList<>();

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
    @PreAuthorize("hasRole('USER')")
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
    @PreAuthorize("hasRole('USER')")
    @DeleteMapping("/{orderDetailId}")
    public ResponseEntity<Map<String, Object>> deleteOrderDetail(@PathVariable("orderDetailId") UUID orderDetailId) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        orderDetailService.deleteOrderDetail(orderDetailId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
