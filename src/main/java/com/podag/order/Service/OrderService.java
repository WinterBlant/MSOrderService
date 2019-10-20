package com.podag.order.Service;

import com.podag.order.OrderStatus;
import com.podag.order.dto.ItemAdditionParametersDTO;
import com.podag.order.dto.OrderDTO;
import com.podag.order.entity.Item;
import com.podag.order.entity.Order;
import com.podag.order.entity.OrderItem;
import com.podag.order.entity.OrderItemKey;
import com.podag.order.repos.ItemRepository;
import com.podag.order.repos.OrderItemRepository;
import com.podag.order.repos.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.naming.directory.InvalidAttributeValueException;
import javax.naming.directory.InvalidSearchFilterException;
import javax.transaction.Transactional;
import java.security.InvalidParameterException;
import java.util.List;

@Component
public class OrderService {

    @Autowired
    OrderRepository orderrepo;
    @Autowired
    ItemRepository itemrepo;
    @Autowired
    OrderItemRepository oirepo;

    public List<Order> findAll() throws InvalidSearchFilterException {
        List<Order> orderlist = orderrepo.findAll();
        if (orderlist.isEmpty()) {
            throw new InvalidSearchFilterException("No orders in database");
        }
        return orderlist;
    }

    public OrderDTO findByID(int orderID) {
        Order order = orderrepo.findById(orderID).orElseThrow(InvalidParameterException::new);
        return new OrderDTO(order.getOrderID(), order.getStatus(), order.getUsername(), order.getTotalAmount(), order.getTotalCost(), order.getOrderItems());
    }

    public OrderDTO addItem(String orderID, ItemAdditionParametersDTO itempar) {
        if (orderID.equals("null") || orderID.equals("")) {
            Order order = new Order(itempar.getUsername(), itempar.getAmount(), itempar.getPrice(),
                    new OrderItem(itemrepo.findById(itempar.getItemId())
                            .orElse(new Item(itempar.getItemId(), itempar.getName(), itempar.getPrice())), itempar.getAmount()));
            orderrepo.save(order);
            return new OrderDTO(order.getOrderID());
        } else {
            Item item = new Item(itempar.getItemId(), itempar.getName(), itempar.getPrice());
            Order ordertoupdate = orderrepo.findById(Integer.parseInt(orderID)).orElse(new Order());
            ordertoupdate.setTotalCost(ordertoupdate.getTotalCost().add(item.getPrice()));
            ordertoupdate.setTotalAmount(ordertoupdate.getTotalAmount() + itempar.getAmount());
            OrderItem orditem = new OrderItem(ordertoupdate, item, itempar.getAmount());
            OrderItemKey oikey = new OrderItemKey(ordertoupdate.getOrderID(), item.getItemId());
            if (oirepo.existsById(oikey)) {
                OrderItem oitem = oirepo.findById(oikey).orElse(new OrderItem());
                oitem.setAmount(oitem.getAmount() + itempar.getAmount());
                oirepo.save(oitem);
            } else {
                ordertoupdate.getOrderItems().add(orditem);
            }
            orderrepo.save(ordertoupdate);
            return new OrderDTO(ordertoupdate.getOrderID());
        }
    }

    @Transactional
    public OrderDTO changeStatus(Integer orderID, String status) throws InvalidAttributeValueException {
        Order ordertoupdate = orderrepo.findById(orderID).orElseThrow(InvalidParameterException::new);
        if (ordertoupdate.getStatus().nextState().contains(OrderStatus.valueOf(status.toUpperCase()))) {
            ordertoupdate.setStatus(OrderStatus.valueOf(status.toUpperCase()));
        } else throw new InvalidAttributeValueException();
        orderrepo.save(ordertoupdate);
        return new OrderDTO(orderID, ordertoupdate.getStatus());
    }
}
