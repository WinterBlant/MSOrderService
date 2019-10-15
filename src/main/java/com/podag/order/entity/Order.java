package com.podag.order.entity;

import com.podag.order.OrderStatus;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int orderID;
    @Column(name = "status")
    private String status;
    @Column(name = "username")
    private String username;
    @Column(name = "totalAmount")
    private int totalAmount;
    @Column(name = "totalCost")
    private BigDecimal totalCost;
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name="orderitem",
        joinColumns = @JoinColumn(name = "OrderID", referencedColumnName = "orderID"),
        inverseJoinColumns = @JoinColumn(name = "ItemID", referencedColumnName = "itemId"))
    private Set<Item> orderItems;

    public Order (){
        this.status = OrderStatus.COLLECTING.toString();
    }
    public Order(String username, int totalAmount, BigDecimal totalCost, Item... items){
        this.status = OrderStatus.COLLECTING.toString();
        this.username = username;
        this.totalAmount = totalAmount;
        this.totalCost = totalCost;
        this.orderItems = Stream.of(items).collect(Collectors.toSet());
    }

    public Order(int orderID, String username, int totalAmount, BigDecimal totalCost, Item... items){
        this.orderID = orderID;
        this.status = OrderStatus.COLLECTING.toString();
        this.username = username;
        this.totalAmount = totalAmount;
        this.totalCost = totalCost;
        this.orderItems = Stream.of(items).collect(Collectors.toSet());
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status.toString();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(int totalAmount) {
        this.totalAmount = totalAmount;
    }

    public BigDecimal getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(BigDecimal totalCost) {
        this.totalCost = totalCost;
    }

    public Set<Item> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(Set<Item> orderItems) {
        this.orderItems = orderItems;
    }
}
