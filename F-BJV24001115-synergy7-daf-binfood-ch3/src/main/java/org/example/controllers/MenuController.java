package org.example.controllers;

import lombok.SneakyThrows;
import org.example.models.Menu;
import org.example.services.MenuService;
import org.example.services.OrderItemService;
import org.example.services.OrderItemServiceImpl;
import org.example.models.OrderItem;
import org.example.services.MenuServiceImpl;
import org.example.views.MenuView;
import org.example.views.OrderItemView;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;

public class MenuController {
    private boolean exit = false;

    MenuView menuView = new MenuView();
    OrderItemView orderItemView = new OrderItemView();

    public void start() {
        displayMenu();
        while (!exit) {
            menuView.inputField();
            int choice = inputSelect();
            switch (choice) {
                case 0 -> {
                    exit = true;
                    System.out.println("Keluar aplikasi. Terima kasih sudah mengunjungi BinarFud!");
                }
                case 1, 2, 3, 4, 5 -> processOrder(choice);
                case 99 -> confirmPayment();
                default -> System.out.println("Pilihan tidak valid. Silahkan pilih kembali.");
            }
        }
    }

    private void displayMenu() {
        MenuService menuService = new MenuServiceImpl();
        Map<Long, Menu> menuMap = menuService.getMenuList();

        menuView.displayMenus(menuMap);
    }

    private int inputSelect() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextInt();
    }

    private void processOrder(int choice) {
        displaySelectedMenu(choice);

        orderItemView.inputField();
        int quantity = inputSelect();

        if (quantity == 0) {
            start();
        }

        addOrder(choice, quantity);

        start();
    }

    public void displaySelectedMenu(int choice) {
        MenuService menuService = new MenuServiceImpl();

        Menu menu = menuService.getAMenu(choice);
        orderItemView.displaySelectedMenu(menu);
    }

    @SneakyThrows
    private Optional<Integer> addOrder(int choice, int quantity) {
        OrderItemService orderItemService = new OrderItemServiceImpl();
        int currentTotalQuantity = orderItemService.getTotalQuantity(choice);

        if (currentTotalQuantity + quantity < 0) {
            orderItemView.minimOrder();
            try {
                Thread.sleep(2000);
            } finally {
                System.out.println("Operation completed.");
            }
        } else {
            orderItemService.create(choice, quantity);
        }

        return Optional.of(currentTotalQuantity);
    }

    private void confirmPayment() {
        OrderItemService orderItemService = new OrderItemServiceImpl();
        List<OrderItem> orderItemList = orderItemService.getOrderItemList();

        orderItemView.confirmPayment(orderItemList);

        menuView.inputField();
        int choice = inputSelect();
        switch (choice) {
            case 0 -> {
                exit = true;
                System.out.println("Keluar aplikasi. Terima kasih sudah mengunjungi BinarFud!");
            }
            case 1 -> receiptPrint();
            case 2 -> start();
            default -> System.out.println("Pilihan tidak valid. Silahkan pilih kembali.");
        }
    }

    private void receiptPrint() {
        OrderItemService orderItemService = new OrderItemServiceImpl();
        List<OrderItem> orderItemList = orderItemService.getOrderItemList();

        orderItemView.receiptPrintView(orderItemList);

        exit = true;
    }

}
