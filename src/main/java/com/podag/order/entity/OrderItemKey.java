package com.podag.order.entity;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class OrderItemKey implements Serializable {
    private int ordID;
    private int itemID;

    public OrderItemKey() {

    }
    public OrderItemKey (int itemID){
        this.itemID = itemID;
    }

    public OrderItemKey (int ordID, int itemID){
        this.ordID = ordID;
        this.itemID = itemID;
    }

    public int getOrd() {
        return ordID;
    }

    public void setOrd(int ordID) {
        this.ordID = ordID;
    }

    public int getItem() {
        return itemID;
    }

    public void setItem(int itemID) {
        this.itemID = itemID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderItemKey that = (OrderItemKey) o;
        return ordID == that.ordID &&
                itemID == that.itemID;
    }

    @Override
    public int hashCode() {
        return Objects.hash(ordID, itemID);
    }
}
