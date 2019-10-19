package com.podag.order.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.podag.order.OrderStatus;
import com.podag.order.entity.OrderItem;

import java.math.BigDecimal;
import java.util.Set;

@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class OrderDTO {
    private int orderID;
    private OrderStatus status;
    private String username;
    private int totalAmount;
    private BigDecimal totalCost;
    private Set<OrderItem> orderItems;

    public OrderDTO(){

    }

    public OrderDTO(int orderID){
        this.orderID = orderID;
    }

    public OrderDTO(int orderID, OrderStatus status){
        this.orderID = orderID;
        this.status = status;
    }

    public OrderDTO(int orderID, OrderStatus status, String username, int totalAmount, BigDecimal totalCost, Set<OrderItem> ordItem){
        this.orderID = orderID;
        this.status = status;
        this.username = username;
        this.totalAmount = totalAmount;
        this.totalCost = totalCost;
        this.orderItems = ordItem;
    }
    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
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
