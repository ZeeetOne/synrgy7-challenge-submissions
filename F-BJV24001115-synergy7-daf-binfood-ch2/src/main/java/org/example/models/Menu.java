package org.example.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor

public class Menu {
    private long id;
    private String code;
    private String name;
    private long price;
    private ItemCategory itemCategory;

    public enum ItemCategory{
        FOOD,
        BAVERAGES,
        OTHERS
    }
}
