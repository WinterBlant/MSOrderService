package com.podag.order.Service;

import com.podag.order.dto.OrderDTO;
import com.podag.order.entity.Order;
import com.podag.order.repos.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.naming.directory.InvalidSearchFilterException;
import java.security.InvalidParameterException;
import java.util.List;
import java.util.Optional;

@Component
public class OrderService {

    @Autowired
    OrderRepository orderrepo;

    public List<Order> findAll() throws InvalidSearchFilterException {
        List<Order> orderlist = orderrepo.findAll();
        if (orderlist.isEmpty()) {
            throw new InvalidSearchFilterException("No orders in database");
        }
        return orderlist;
    }

    public OrderDTO findByID(int orderID){
        Order order = orderrepo.findById(orderID).orElseThrow(InvalidParameterException::new);
        return new OrderDTO(order.getOrderID(),order.getStatus(),order.getUsername(),order.getTotalAmount(),order.getTotalCost(),order.getOrderItems());
    }
}
