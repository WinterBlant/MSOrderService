package com.podag.order.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "item")
public class Item {
    @EmbeddedId
    private ItemUserID itemId;
    @Column(name = "name")
    private String name;
    @Column(name = "amount")
    private int amount;
    @Column(name = "price")
    private BigDecimal price;
    @ManyToMany(mappedBy = "orderItems")
    private Set<Order> orders = new HashSet<>();

    public Item(){
    }

    public Item(ItemUserID itemId, String name, int amount, BigDecimal price){
        this.itemId = itemId;
        this.name = name;
        this.amount = amount;
        this.price = price;
    }

    public ItemUserID getItemId() {
        return itemId;
    }

    public void setItemId(ItemUserID itemId) {
        this.itemId = itemId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Set<Order> getOrders() {
        return orders;
    }

    public void setOrders(Set<Order> orders) {
        this.orders = orders;
    }
}
