package com.podag.order.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name="orderitem")
public class OrderItem implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long Id_OI;

    //@Id
    @ManyToOne
    @JoinColumn
    private Order ord;

    //@Id
    @ManyToOne
    @JoinColumn
    private Item item;

    private int amount;

    public OrderItem(){

    }
    public OrderItem (Item item, int amount){
        this.item = item;
        this.amount = amount;
    }

    public OrderItem (Order ord, Item item, int amount){
        this.ord = ord;
        this.item = item;
        this.amount = amount;
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

    public Long getId_OI() {
        return Id_OI;
    }

    public void setId_OI(Long id_OI) {
        Id_OI = id_OI;
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
