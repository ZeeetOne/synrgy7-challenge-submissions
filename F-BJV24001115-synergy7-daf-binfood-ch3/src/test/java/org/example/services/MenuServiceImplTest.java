package org.example.services;

import java.util.Map;
import org.example.Data;
import org.example.models.Menu;
import org.example.models.Menu.ItemCategory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MenuServiceImplTest {

    MenuService menuService;

    @BeforeAll
    static void initiateData() {
        Data.initiateData();
    }

    @BeforeEach
    void initiateObj() {
        menuService = new MenuServiceImpl();
    }

    @Test
    void getMenuList() {
        Map<Long, Menu> menuMap = menuService.getMenuList();
        Assertions.assertEquals(Data.menuMap, menuMap);
    }

    @Test
    void getAMenu() {
        Menu nh = new Menu(6L, "NH", "Nasi Hoheng", 8000L, ItemCategory.FOOD);
        Data.menuMap.put(nh.getId(), nh);
        int position = Data.menuMap.size();
        Menu menu = menuService.getAMenu(position);
        Assertions.assertEquals(nh, menu);
    }
}