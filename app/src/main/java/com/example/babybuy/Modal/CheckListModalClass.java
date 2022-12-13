package com.example.babybuy.Modal;

public class CheckListModalClass {
    private int itemImage;
    private String itemName;
    private int addItemImage;

    public CheckListModalClass(int itemImage, String itemName, int addItemImage) {
        this.itemImage = itemImage;
        this.itemName = itemName;
        this.addItemImage = addItemImage;
    }

    public int getItemImage() {
        return itemImage;
    }

    public String getItemName() {
        return itemName;
    }

    public int getAddItemImage() {
        return addItemImage;
    }
}
