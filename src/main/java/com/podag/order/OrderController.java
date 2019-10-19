package com.podag.order;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.podag.order.Service.OrderService;
import com.podag.order.dto.ItemAdditionParametersDTO;
import com.podag.order.dto.OrderDTO;
import com.podag.order.entity.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.naming.directory.InvalidSearchFilterException;
import java.security.InvalidParameterException;
import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService ordserv;

    @GetMapping
    public ResponseEntity<List<Order>> getOrders() {
        try {
            return ResponseEntity.ok().body(ordserv.findAll());
        }
        catch (InvalidSearchFilterException e) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("{orderid}")
    public ResponseEntity<OrderDTO> getOrderById(@PathVariable(value = "orderid") Integer orderID) {
        try {
            return ResponseEntity.ok().body(ordserv.findByID(orderID));
        } catch (InvalidParameterException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value = "{orderid}/item", consumes = "application/json")
    public ResponseEntity<OrderDTO> addToOrder (@PathVariable(value = "orderid") String orderID, @RequestBody String data){
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            ItemAdditionParametersDTO itempar = objectMapper.readValue(data, ItemAdditionParametersDTO.class);
            return ResponseEntity.ok().body(ordserv.addItem(orderID, itempar));
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "{orderid}/status/{status}", method = RequestMethod.PUT)
    public ResponseEntity<OrderDTO> changeOrderStatus (@PathVariable(value = "orderid") Integer orderID, @PathVariable(value = "status") String status) {
        try{
            return ResponseEntity.ok().body(ordserv.changeStatus(orderID, status));
        } catch (InvalidParameterException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
