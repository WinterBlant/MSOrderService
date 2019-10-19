package com.podag.order;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.podag.order.Service.OrderService;
import com.podag.order.dto.ItemAdditionParametersDTO;
import com.podag.order.dto.OrderDTO;
import com.podag.order.entity.Item;
import com.podag.order.entity.Order;
import com.podag.order.entity.OrderItem;
import com.podag.order.entity.OrderItemKey;
import com.podag.order.repos.ItemRepository;
import com.podag.order.repos.OrderItemRepository;
import com.podag.order.repos.OrderRepository;
import jdk.net.SocketFlow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpStatusCodeException;

import javax.naming.directory.InvalidSearchFilterException;
import java.io.IOException;
import java.security.InvalidParameterException;
import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderRepository orderrepo;

    @Autowired
    private OrderItemRepository oirepo;

    @Autowired
    private ItemRepository itemrepo;

    @Autowired
    private OrderService ordserv;

    @GetMapping
    public ResponseEntity<List<Order>> getOrders() {
        try {
            return ResponseEntity.ok().body(ordserv.findAll());
        }
        catch (InvalidSearchFilterException e) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("{orderid}")
    public ResponseEntity<OrderDTO> getOrderById(@PathVariable(value = "orderid") Integer orderID) {
        try {
            return ResponseEntity.ok().body(ordserv.findByID(orderID));
        } catch (InvalidParameterException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(value = "{orderid}/item", consumes = "application/json")
    public ResponseEntity<OrderDTO> addToOrder (@PathVariable(value = "orderid") String orderID, @RequestBody String data){
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            ItemAdditionParametersDTO itempar = objectMapper.readValue(data, ItemAdditionParametersDTO.class);
            if (orderID.equals("null") || orderID.equals("")) {
                    Order order = new Order(itempar.getUsername(), itempar.getAmount(), itempar.getPrice(), new OrderItem(itemrepo.findById(itempar.getItemId()).orElse(new Item(itempar.getItemId(), itempar.getName(), itempar.getPrice())), itempar.getAmount()));
                    orderrepo.save(order);
                    OrderDTO odto = new OrderDTO(order.getOrderID());
                    return ResponseEntity.ok().body(odto);
            } else {
                Item item = new Item(itempar.getItemId(), itempar.getName(), itempar.getPrice());
                Order ordertoupdate = orderrepo.findById(new Integer(orderID)).orElse(new Order());
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
                OrderDTO odto = new OrderDTO(ordertoupdate.getOrderID());
                return ResponseEntity.ok().body(odto);
            }
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "{orderid}/status/{status}", method = RequestMethod.PUT)
    public void changeOrderStatus (@PathVariable(value = "orderid") Integer orderID, @PathVariable(value = "status") String status) {
        Order ordertoupdate = orderrepo.getOne(orderID);
        ordertoupdate.setStatus(OrderStatus.valueOf(status.toUpperCase()));
        orderrepo.save(ordertoupdate);
    }
}
