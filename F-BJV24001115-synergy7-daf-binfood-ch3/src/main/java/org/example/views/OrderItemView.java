package org.example.views;

import org.example.models.Menu;
import org.example.models.OrderItem;
import org.example.utils.StringFormat;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import static org.example.utils.StringFormat.SEPARATOR_LINE;

public class OrderItemView {
    public void displaySelectedMenu(Menu menu) {
        System.out.println(SEPARATOR_LINE);
        System.out.print("Berapa pesanan anda");
        System.out.println(SEPARATOR_LINE);
        System.out.println(StringFormat.orderItemFormat(menu));
        System.out.println("(input 0 untuk kembali)");
    }

    public void inputField() {
        System.out.print("\nqty => ");
    }

    public void confirmPayment(List<OrderItem> orderItemList) {
        System.out.println("\n==========================");
        System.out.println("Konfirmasi & Pembayaran");
        System.out.println("==========================\n");

        int totalQuantity = 0;
        int totalCost = 0;

        for (OrderItem orderItem : orderItemList) {
            totalQuantity += orderItem.getQuantity();
            totalCost += orderItem.getMenu().getPrice() * orderItem.getQuantity();
            System.out.println(StringFormat.orderItemListFormat(orderItem));
        }

        System.out.println("----------------------------+");
        System.out.println(StringFormat.totalOrderItemFormat(totalQuantity, totalCost));

        System.out.println("\n1. Konfirmasi dan Bayar");
        System.out.println("2. Kembali ke menu utama");
        System.out.println("0. Keluar aplikasi");
    }

    public void receiptPrintView(List<OrderItem> orderItemList) {
        try(BufferedWriter writer = new BufferedWriter(new FileWriter("receipt.txt"))) {
            writer.write(SEPARATOR_LINE + "\n");
            writer.write("BinarFud");
            writer.write(SEPARATOR_LINE + "\n");
            writer.write("\nTerima kasih sudah memesan \ndi BinarFud\n");
            writer.write("\nDibawah ini adalah pesanan anda\n");

            System.out.println(SEPARATOR_LINE);
            System.out.print("BinarFud");
            System.out.println(SEPARATOR_LINE);
            System.out.println("\nTerima kasih sudah memesan \ndi BinarFud");
            System.out.println("\nDibawah ini adalah pesanan anda\n");

            int totalQuantity = 0;
            int totalCost = 0;

            for (OrderItem orderItem : orderItemList) {
                totalQuantity += orderItem.getQuantity();
                totalCost += orderItem.getMenu().getPrice() * orderItem.getQuantity();
                String orderItemText = StringFormat.orderItemListFormat(orderItem);
                writer.write(orderItemText + "\n");
                System.out.println(orderItemText);
            }

            writer.write("----------------------------+\n");
            System.out.println("----------------------------+");

            String totalOrderText = StringFormat.totalOrderItemFormat(totalQuantity, totalCost);
            writer.write(totalOrderText + "\n");
            System.out.println(totalOrderText);

            writer.write("\nPembayaran : BinarCash\n");
            System.out.println("\nPembayaran : BinarCash");

            writer.write("\n==========================\n");
            writer.write("SImpan struk ini sebagai \nbukti pembayaran\n");
            writer.write("==========================\n");

            System.out.println("\n==========================");
            System.out.println("SImpan struk ini sebagai \nbukti pembayaran");
            System.out.println("==========================");
        } catch (IOException e) {
            System.out.println("Error saving receipt to file: " + e.getMessage());
        }
    }

    public void minimOrder() {
        System.out.println(SEPARATOR_LINE);
        System.out.print("Minimal 1 jumlah \npesanan!");
        System.out.println(SEPARATOR_LINE + "\n");
    }
}
