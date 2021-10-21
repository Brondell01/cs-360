package com.zybooks.module5application;

import java.io.Serializable;

public class InventoryItem implements Serializable {
    private String itemName;
    private int itemCount;

    public InventoryItem() {
    }

    public InventoryItem(String itemName, int itemCount) {
        this.itemName = itemName;
        this.itemCount = itemCount;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getItemCount() {
        return itemCount;
    }

    public void setItemCount(int itemCount) {
        this.itemCount = itemCount;
    }
}
