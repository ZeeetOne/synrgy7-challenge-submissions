package com.binarfud.binarfud_service.service;

import com.binarfud.binarfud_service.dto.requests.OrderRequestCreateDtos;
import com.binarfud.binarfud_service.dto.responses.OrderResponseDtos;

public interface OrderFacade {
    OrderResponseDtos saveOrderWithDetails(OrderRequestCreateDtos orderDto);
}
