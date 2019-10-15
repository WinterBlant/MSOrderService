package com.podag.order;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.podag.order.dto.ItemAdditionParametersDTO;
import com.podag.order.entity.Item;
import com.podag.order.entity.ItemUserID;
import com.podag.order.entity.Order;
//import com.podag.order.entity.OrderItem;
import com.podag.order.repos.ItemRepository;
//import com.podag.order.repos.OrderItemRepository;
import com.podag.order.repos.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/orders")
public class OrderController {


    @Autowired
    private OrderRepository orderrepo;

    @GetMapping
    public List<Order> getOrders() {
        return orderrepo.findAll();
    }

//    @RequestMapping(value = "dododi/dododo", method = RequestMethod.GET)
//    @ResponseBody
//    public HttpStatus additem() {
//        Order order = new Order(145412);
//        orderrepo.save(order);
//        return HttpStatus.CREATED;
//    }

    @GetMapping("{orderid}")
    public ResponseEntity<Order> getOrderById(@PathVariable(value = "orderid") Integer orderID) {
        Order order = orderrepo.findById(orderID).orElse(new Order());
        return ResponseEntity.ok().body(order);
    }

    @PostMapping(value = "{orderid}/item", consumes = "application/json")
    @ResponseBody
    public void addToOrder (@PathVariable(value = "orderid") String orderID, @RequestBody String data) throws IOException {
        if (orderID.equals("null") || orderID.equals("")){
            ObjectMapper objectMapper = new ObjectMapper();
            ItemAdditionParametersDTO itempar = objectMapper.readValue(data, ItemAdditionParametersDTO.class);
            ItemUserID iuid = new ItemUserID(itempar.getItemId(), itempar.getUsername());
            Item item = new Item(iuid, itempar.getName(), itempar.getAmount(), itempar.getPrice());
            Order order = new Order(itempar.getUsername(), itempar.getAmount(), itempar.getPrice(), item);
            orderrepo.save(order);
        } else {
            ObjectMapper objectMapper = new ObjectMapper();
            ItemAdditionParametersDTO itempar = objectMapper.readValue(data, ItemAdditionParametersDTO.class);
            ItemUserID iuid = new ItemUserID(itempar.getItemId(), itempar.getUsername());
            Item item = new Item(iuid, itempar.getName(), itempar.getAmount(), itempar.getPrice());
            Order ordertoupdate = orderrepo.getOne(new Integer(orderID));
            ordertoupdate.setTotalAmount(ordertoupdate.getTotalAmount()+item.getAmount());
            ordertoupdate.setTotalCost(ordertoupdate.getTotalCost().add(item.getPrice()));
            ordertoupdate.getOrderItems().add(item);
            orderrepo.save(ordertoupdate);
        }
    }



}
