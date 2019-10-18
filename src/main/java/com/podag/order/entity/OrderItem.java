package com.podag.order.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name="orderitem")
@JsonIgnoreProperties({"order","orditID"})
public class OrderItem{
    @EmbeddedId
    private OrderItemKey orditID;

    @ManyToOne
    @MapsId("ordID")
    private Order ord;

    @ManyToOne
    @MapsId("itemID")
    private Item item;

    private int amount;

    public OrderItem(){

    }
    public OrderItem (Item item, int amount){
        this.item = item;
        this.amount = amount;
        this.orditID = new OrderItemKey(item.getItemId());
    }

    public OrderItem (Order ord, Item item, int amount){
        this.ord = ord;
        this.item = item;
        this.amount = amount;
        this.orditID = new OrderItemKey(ord.getOrderID(), item.getItemId());
    }
    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Order getOrder() {
        return ord;
    }

    public void setOrder(Order order) {
        this.ord = order;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public OrderItemKey getOrditID() {
        return orditID;
    }

    public void setOrditID(OrderItemKey orditID) {
        this.orditID = orditID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderItem orderItem = (OrderItem) o;
        return amount == orderItem.amount &&
                ord.equals(orderItem.ord) &&
                item.equals(orderItem.item);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ord, item, amount);
    }
}
