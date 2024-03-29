package org.example.views;

import org.example.models.Menu;

import java.util.Map;
import java.util.Set;

import static org.example.utils.StringFormat.menuFormat;

public class MenuView {
    public void displayMenus(Map<Long, Menu> menuMap) {
        System.out.println("\n==========================");
        System.out.println("Selamat datang di BinarFud");
        System.out.println("==========================");
        System.out.println("\nSilahkan pilih makanan :");

        Set<Long> setId = menuMap.keySet();
        for (Long key : setId) {
            System.out.println(menuFormat(menuMap.get(key)));
        }

        System.out.println("99. Pesan dan Bayar");
        System.out.println("0. Keluar aplikasi");
    }

    public void inputField() {
        System.out.print("\n=> ");
    }
}
