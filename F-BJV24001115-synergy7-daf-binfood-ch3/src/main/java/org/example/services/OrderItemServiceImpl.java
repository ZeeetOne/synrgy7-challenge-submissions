package org.example.services;

import org.example.Data;
import org.example.models.Menu;
import org.example.models.OrderItem;

import java.util.ArrayList;
import java.util.List;

public class OrderItemServiceImpl implements OrderItemService{
    private long nextId = 1;

    @Override
    public OrderItem create(int key, int quantity) {
        OrderItem orderItem = new OrderItem();

        Long menuKey = new ArrayList<>(Data.menuMap.keySet()).get(key - 1);
        Menu menu = Data.menuMap.get(menuKey);

        orderItem.setId(nextId++)
                .setMenu(menu)
                .setQuantity(quantity);

        Data.orderItemList.add(orderItem);

        return orderItem;
    }

    @Override
    public List<OrderItem> getOrderItemList() {
        return Data.orderItemList;
    }

    @Override
    public int getTotalQuantity(int choice) {
//        int totalQuantity = 0;
//        for (OrderItem orderItem: getOrderItemList()) {
//            if (orderItem.getMenu().getId() == choice) {
//                totalQuantity += orderItem.getQuantity();
//            }
//        }
//        return totalQuantity;

        return getOrderItemList().stream()
            .filter(orderItem -> orderItem.getMenu().getId() == choice)
            .mapToInt(OrderItem::getQuantity)
            .sum();
    }
}
