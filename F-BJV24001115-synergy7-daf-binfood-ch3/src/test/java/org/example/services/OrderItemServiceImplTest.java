package org.example.services;

import java.util.ArrayList;
import java.util.List;
import org.example.Data;
import org.example.models.Menu;
import org.example.models.OrderItem;
import org.example.models.Menu.ItemCategory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OrderItemServiceImplTest {
    private List<OrderItem> orderItemList;
    OrderItemService orderItemService;

    @BeforeEach
    void iniate() {
        orderItemService = new OrderItemServiceImpl();
        orderItemList = new ArrayList();
        OrderItem testOrder = new OrderItem(1L, new Menu(6L, "NH", "Nasi Hoheng", 8000L, ItemCategory.FOOD), 2);
        Data.orderItemList.add(testOrder);
    }

    @Test
    void create() {
        int initialSize = Data.orderItemList.size();
        OrderItem createdOrderItem = orderItemService.create(2, 1);
        Assertions.assertEquals(createdOrderItem, Data.orderItemList.get(initialSize));
    }

    @Test
    void getOrderItemList() {
        orderItemService = new OrderItemServiceImpl();
        List<OrderItem> orderItemList = orderItemService.getOrderItemList();
        Assertions.assertEquals(Data.orderItemList, orderItemList);
    }

    @Test
    void getTotalQuantity() {
        OrderItemService orderItemService = new OrderItemServiceImpl();
        int totalQuantityChoice = orderItemService.getTotalQuantity(2);
        Assertions.assertEquals(1, totalQuantityChoice);
    }
}