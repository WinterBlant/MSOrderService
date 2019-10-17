package com.podag.order;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.podag.order.dto.ItemAdditionParametersDTO;
import com.podag.order.entity.Item;
import com.podag.order.entity.Order;
import com.podag.order.entity.OrderItem;
import com.podag.order.repos.ItemRepository;
import com.podag.order.repos.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderRepository orderrepo;

    @Autowired
    private ItemRepository itemrepo;

    @GetMapping
    public List<Order> getOrders() {
        return orderrepo.findAll();
    }

    @GetMapping("{orderid}")
    public ResponseEntity<Order> getOrderById(@PathVariable(value = "orderid") Integer orderID) {
        Order order = orderrepo.findById(orderID).orElse(new Order());
        return ResponseEntity.ok().body(order);
    }

    @PostMapping(value = "{orderid}/item", consumes = "application/json")
    @ResponseBody
    public void addToOrder (@PathVariable(value = "orderid") String orderID, @RequestBody String data) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        ItemAdditionParametersDTO itempar = objectMapper.readValue(data, ItemAdditionParametersDTO.class);
        if (orderID.equals("null") || orderID.equals("")){
            Item item = new Item(itempar.getItemId(), itempar.getName(), itempar.getPrice());
            itemrepo.save(item);
            orderrepo.save(new Order(itempar.getUsername(), itempar.getAmount(), itempar.getPrice(), new OrderItem(item, itempar.getAmount())));
        } else {
            Item item = new Item(itempar.getItemId(), itempar.getName(), itempar.getPrice());
            itemrepo.save(item);
            Order ordertoupdate = orderrepo.getOne(new Integer(orderID));
            ordertoupdate.setTotalCost(ordertoupdate.getTotalCost().add(item.getPrice()));
            ordertoupdate.setTotalAmount(ordertoupdate.getTotalAmount()+itempar.getAmount());
            OrderItem orditem = new OrderItem(ordertoupdate, item, itempar.getAmount());
            ordertoupdate.getOrderItems().add(orditem);
            orderrepo.save(ordertoupdate);
        }
    }

    @RequestMapping(value = "{orderid}/status/{status}", method = RequestMethod.PUT)
    public void changeOrderStatus (@PathVariable(value = "orderid") Integer orderID, @PathVariable(value = "status") String status) {
        Order ordertoupdate = orderrepo.getOne(new Integer(orderID));
        ordertoupdate.setStatus(OrderStatus.valueOf(status));
        orderrepo.save(ordertoupdate);
    }
}
