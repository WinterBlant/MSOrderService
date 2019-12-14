package com.podag.order.dto;

import java.io.Serializable;

public class WarehouseDTO implements Serializable {
    private int orderID;
    private int itemID;
    private int amount;

    public WarehouseDTO(int orderID, int itemID, int amount) {
        this.orderID = orderID;
        this.itemID = itemID;
        this.amount = amount;
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public int getItemID() {
        return itemID;
    }

    public void setItemID(int itemID) {
        this.itemID = itemID;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
