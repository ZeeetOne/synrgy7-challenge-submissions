package org.example;

import org.example.models.Menu;
import org.example.models.OrderItem;

import java.util.*;

public class Data {
    private Data() {}
    public static final Map<Long, Menu> menuMap = new HashMap<>();
    public static final List<OrderItem> orderItemList = new ArrayList<>();

    public static void initiateData() {
        Menu ng = new Menu(1L, "NG", "Nasi Goreng", 15000, Menu.ItemCategory.FOOD);
        Menu mg = new Menu(2L, "MG", "Mie Goreng", 13000, Menu.ItemCategory.FOOD);
        Menu na = new Menu(3L, "NA", "Nasi + Ayam", 18000, Menu.ItemCategory.FOOD);
        Menu etm = new Menu(4L, "ETM", "Es Teh Manis", 3000, Menu.ItemCategory.BAVERAGES);
        Menu ej = new Menu(5L, "EJ", "Es Jeruk", 5000, Menu.ItemCategory.BAVERAGES);

        menuMap.put(ng.getId(), ng);
        menuMap.put(mg.getId(), mg);
        menuMap.put(na.getId(), na);
        menuMap.put(etm.getId(), etm);
        menuMap.put(ej.getId(), ej);
    }
}
