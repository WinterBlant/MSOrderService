package com.podag.order.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="item")
@JsonIgnoreProperties("orders")
public class Item {
    @Id
    private int itemId;
    @Column(name = "name")
    private String name;
    @Column(name = "price")
    private BigDecimal price;
    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<OrderItem> orders = new HashSet<>();

    public Item(){
    }

    public Item(int itemId, String name, BigDecimal price){
        this.itemId = itemId;
        this.name = name;
        this.price = price;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Set<OrderItem> getOrders() {
        return orders;
    }

    public void setOrders(Set<OrderItem> orders) {
        this.orders = orders;
    }
}
