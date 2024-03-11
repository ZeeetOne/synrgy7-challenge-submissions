package org.example;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

class OrderItem {
    private final String itemName;
    private final int itemPrice;
    private int quantity;

    public OrderItem(String itemName, int itemPrice, int quantity) {
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.quantity = quantity;
    }

    public String getItemName() {
        return itemName;
    }

    public int getTotalPricePerItem() {
        return quantity * itemPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}

public class Main {
    private static final Map<String, Integer> foods = new LinkedHashMap<>();
    private static final Map<String, Integer> drinks = new LinkedHashMap<>();
    private static final Map<String, Integer> foodsAndDrinks = new LinkedHashMap<>();
    private static final List<OrderItem> orderList = new ArrayList<>();

    static {
        foods.put("Nasi Goreng", 15000);
        foods.put("Mie Goreng", 13000);
        foods.put("Nasi + Ayam", 18000);
        drinks.put("Es Teh Manis", 3000);
        drinks.put("Es Jeruk", 5000);
        foodsAndDrinks.putAll(foods);
        foodsAndDrinks.putAll(drinks);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int choice;

        do {
            showMenu();
            System.out.print("\n=> "); choice = scanner.nextInt();

            switch (choice) {
                case 1, 2, 3, 4, 5:
                    processOrder(choice, scanner);
                    break;
                case 99:
                    processPayment(scanner);
                    break;
                case 0:
                    System.out.println("Keluar aplikasi. Terima kasih sudah mengunjungi BinarFud!");
                    break;
                default:
                    System.out.println("Pilihan tidak valid. Silahkan pilih kembali.");
            }
        } while (choice != 0);
    }

    private static void showMenu() {
        System.out.println("\n==========================");
        System.out.println("Selamat datang di BinarFud");
        System.out.println("==========================");
        System.out.println("\nSilahkan pilih makanan :");

        AtomicInteger count = new AtomicInteger(1);
        foodsAndDrinks.forEach((key, value) -> System.out.println(String.format("%1d. %-12s | %,6d", count.getAndIncrement(), key, value)));

        System.out.println("99. Pesan dan Bayar");
        System.out.println("0. Keluar aplikasi");
    }

    private static void processOrder(int menuItem, Scanner scanner) {
        String itemName = new ArrayList<>(foodsAndDrinks.keySet()).get(menuItem - 1);
        int itemPrice = foodsAndDrinks.get(itemName);

        System.out.println("\n==========================");
        System.out.println("Berapa pesanan anda");
        System.out.println("==========================");
        System.out.println(String.format("\n%-12s | %,6d", itemName, itemPrice));
        System.out.println("(input 0 untuk kembali)");
        System.out.print("\nqty => "); int quantity = scanner.nextInt();

        if (quantity == 0) {
            return;
        }

        boolean orderExists = orderList.stream()
                .filter(existingOrder -> existingOrder.getItemName().equals(itemName))
                .peek(existingOrder -> existingOrder.setQuantity(existingOrder.getQuantity() + quantity))
                .findFirst()
                .isPresent();

        if (!orderExists) {
            OrderItem orderItem = new OrderItem(itemName, itemPrice, quantity);
            orderList.add(orderItem);
        }
    }

    private static void processPayment(Scanner scanner) {
        System.out.println("\n==========================");
        System.out.println("Konfirmasi & Pembayaran");
        System.out.println("==========================\n");

        if (orderList.isEmpty()) {
            System.out.println("Anda belum memesan apapun.\n");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return;
        }

        showOrderDetail();

        int choice;

        System.out.println("\n1. Konfirmasi dan Bayar");
        System.out.println("2. Kembali ke menu utama");
        System.out.println("0. Keluar aplikasi");
        System.out.print("\n=> "); choice = scanner.nextInt();

        switch (choice) {
            case 1:
                receipt();
                break;
            case 2:
                return;
            case 0:
                System.out.println("Keluar aplikasi. Sampai jumpa!");
                System.exit(0);
                break;
            default:
                System.out.println("Pilihan tidak valid. Silahkan pilih kembali.");
        }
    }

    private static void showOrderDetail() {
        int totalQuantity = 0;
        int totalCost = 0;

        for (OrderItem orderItem : orderList) {
            totalQuantity += orderItem.getQuantity();
            totalCost += orderItem.getTotalPricePerItem();
            System.out.println(String.format("%-12s %2d %,10d", orderItem.getItemName(), orderItem.getQuantity(), orderItem.getTotalPricePerItem()));
        }

        System.out.println("----------------------------+");
        System.out.println(String.format("%-12s %2d %,10d", "Total", totalQuantity, totalCost));
    }

    private static void receipt() {
        System.out.println("\n==========================");
        System.out.println("BinarFud");
        System.out.println("==========================");
        System.out.println("\nTerima kasih sudah memesan \ndi BinarFud");
        System.out.println("\nDibawah ini adalah pesanan anda\n");

        showOrderDetail();

        System.out.println("\nPembayaran : BinarCash");
        System.out.println("\n==========================");
        System.out.println("SImpan struk ini sebagai \nbukti pembayaran");
        System.out.println("==========================");

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        printRecipt();
    }

    private static void printRecipt() {
        try (PrintWriter writer = new PrintWriter(new FileWriter("receipt.txt"))) {
            writer.println("==========================");
            writer.println("BinarFud");
            writer.println("==========================");
            writer.println("\nTerima kasih sudah memesan \ndi BinarFud");
            writer.println("\nDibawah ini adalah pesanan anda\n");

            showOrderDetail(writer);

            writer.println("\nPembayaran : BinarCash");
            writer.println("\n==========================");
            writer.println("SImpan struk ini sebagai \nbukti pembayaran");
            writer.println("==========================");
        } catch (IOException e) {
            System.out.println("Error saving receipt to file: " + e.getMessage());
        }

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.exit(0);
    }

    private static void showOrderDetail(PrintWriter writer) {
        int totalQuantity = 0;
        int totalCost = 0;

        for (OrderItem orderItem : orderList) {
            totalQuantity += orderItem.getQuantity();
            totalCost += orderItem.getTotalPricePerItem();
            writer.println(String.format("%-12s %2d %,10d", orderItem.getItemName(), orderItem.getQuantity(), orderItem.getTotalPricePerItem()));
        }

        writer.println("----------------------------+");
        writer.println(String.format("%-12s %2d %,10d", "Total", totalQuantity, totalCost));
    }
}