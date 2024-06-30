package com.binarfud.binarfud_service.service;

import com.binarfud.binarfud_service.dto.OrderCompletedEventDto;
import com.binarfud.binarfud_service.entity.Order;
import com.binarfud.binarfud_service.repository.ProductRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class MessageProducer {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ProductRepository productRepository;

    public void sendOrderCompletedEvent(Order order) {
        try {
            OrderCompletedEventDto eventDto = new OrderCompletedEventDto();
            eventDto.setId(order.getId());
            eventDto.setOrderTime(order.getOrderTime());
            eventDto.setUserEmail(order.getUser().getEmailAddress());

            // Convert order details to DTOs with product names
            List<OrderCompletedEventDto.OrderCompletedDetailDto> orderDetails = order.getOrderDetails().stream()
                    .map(detail -> {
                        OrderCompletedEventDto.OrderCompletedDetailDto detailDto = new OrderCompletedEventDto.OrderCompletedDetailDto();
                        detailDto.setProductId(detail.getProduct().getId());
                        detailDto.setProductName(detail.getProduct().getName());
                        detailDto.setQuantity(detail.getQuantity());
                        detailDto.setPrice(detail.getTotalPrice());
                        return detailDto;
                    })
                    .collect(Collectors.toList());
            eventDto.setOrderDetails(orderDetails);

            String eventJson = objectMapper.writeValueAsString(eventDto);
            kafkaTemplate.send("order-completed", eventJson);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
