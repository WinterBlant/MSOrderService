package com.podag.order;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.podag.order.Service.OrderService;
import com.podag.order.dto.ItemAdditionParametersDTO;
import com.podag.order.dto.OrderDTO;
import com.podag.order.entity.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.naming.directory.InvalidAttributeValueException;
import javax.naming.directory.InvalidSearchFilterException;
import java.io.IOException;
import java.security.InvalidParameterException;
import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private static final Logger LOGGER= LoggerFactory.getLogger(OrderController.class);

    @Autowired
    private OrderService ordserv;

    @GetMapping
    public ResponseEntity<List<Order>> getOrders() throws InvalidSearchFilterException, InterruptedException {
        LOGGER.info("Calling method getOrders");
        return ResponseEntity.ok().body(ordserv.findAll());
    }

    @GetMapping("{orderid}")
    public ResponseEntity<OrderDTO> getOrderById(@PathVariable(value = "orderid") Integer orderID) {
        LOGGER.info("Calling method getOrderById with orderID = {}", orderID);
        return ResponseEntity.ok().body(ordserv.findByID(orderID));
    }

    @PostMapping(value = "{orderid}/item", consumes = "application/json")
    public ResponseEntity<OrderDTO> addToOrder (@PathVariable(value = "orderid") String orderID, @RequestBody String data) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        ItemAdditionParametersDTO itempar = objectMapper.readValue(data, ItemAdditionParametersDTO.class);
        LOGGER.info("Calling method addToOrder with orderID = {} and item with name = {} and price = {}", orderID, itempar.getName(), itempar.getPrice());
        return ResponseEntity.ok().body(ordserv.addItem(orderID, itempar));
    }

    @RequestMapping(value = "{orderid}/status/{status}", method = RequestMethod.PUT)
    public ResponseEntity<OrderDTO> changeOrderStatus (@PathVariable(value = "orderid") Integer orderID, @PathVariable(value = "status") String status) {
        try{
            LOGGER.info("Calling method changeOrderStatus with orderID = {} and status = {}", orderID, status);
            return ResponseEntity.ok().body(ordserv.changeStatus(orderID, status));
        } catch (InvalidParameterException e){
            LOGGER.error("No order with such ID exists");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (IllegalArgumentException e) {
            LOGGER.error("No status with such status name exists");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (InvalidAttributeValueException e) {
            LOGGER.error("Can't go to status = {}; check the state machine", status);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
