package org.example;

import org.example.controllers.MenuController;

public class Main {
    public static void main(String[] args) {
        Data.initiateData();

        MenuController menuController = new MenuController();
        menuController.start();
    }
}