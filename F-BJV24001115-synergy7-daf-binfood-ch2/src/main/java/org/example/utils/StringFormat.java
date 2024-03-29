package org.example.utils;

import org.example.models.Menu;
import org.example.models.OrderItem;

public class StringFormat {
    private StringFormat() {}
    public static final String SEPARATOR_LINE = "\n==========================";
    public static String menuFormat(Menu menu) {
        return String.format("%1d. %-12s | %,6d", menu.getId(), menu.getName(), menu.getPrice());
    }

    public static String orderItemFormat(Menu menu) {
        return String.format("%n%-12s | %,6d", menu.getName(), menu.getPrice());
    }

    public static String orderItemListFormat(OrderItem orderItem) {
        return String.format("%-12s %2d %,10d", orderItem.getMenu().getName(), orderItem.getQuantity(), orderItem.getMenu().getPrice() * orderItem.getQuantity());
    }

    public static String totalOrderItemFormat(int totalQuantity, int totalCost) {
        return String.format("%-12s %2d %,10d", "Total", totalQuantity, totalCost);
    }
}
