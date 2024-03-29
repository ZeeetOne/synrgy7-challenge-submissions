package org.example.services;

import org.example.models.Menu;

import java.util.Map;

public interface MenuService {
    Map<Long, Menu> getMenuList();

    Menu getAMenu(int choice);
}
