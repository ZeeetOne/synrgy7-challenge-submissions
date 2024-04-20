package org.example.services;

import org.example.Data;
import org.example.models.Menu;

import java.util.ArrayList;
import java.util.Map;


public class MenuServiceImpl implements MenuService  {
    @Override
    public Map<Long, Menu> getMenuList() {
        return Data.menuMap;
    }

    @Override
    public Menu getAMenu(int choice) {
        Long key = new ArrayList<>(Data.menuMap.keySet()).get(choice - 1);
        return Data.menuMap.get(key);
    }
}
