package org.example.services;

import org.example.models.OrderItem;

import java.util.List;

public interface OrderItemService {
    OrderItem create(int key, int quantity);

    List<OrderItem> getOrderItemList();

    int getTotalQuantity(int choice);
}
