package com.podag.order.entity;

import com.podag.order.OrderStatus;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Entity
@Table(name="Orders")
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
    @OneToMany(mappedBy = "ord", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<OrderItem> orderItems;

    public Order (){
        this.status = OrderStatus.COLLECTING.toString();
    }
    public Order(String username, int totalAmount, BigDecimal totalCost, OrderItem... items){
        this.status = OrderStatus.COLLECTING.toString();
        this.username = username;
        this.totalAmount = totalAmount;
        this.totalCost = totalCost;
        for(OrderItem orderitems : items) orderitems.setOrder(this);
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

    public Set<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(Set<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }
}
