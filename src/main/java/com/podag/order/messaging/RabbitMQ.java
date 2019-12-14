package com.podag.order.messaging;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQ {
    @Bean
    public Declarables exchanges() {
        FanoutExchange statusExchange = new FanoutExchange("statusExchange");
        DirectExchange paymentOrderStatus = new DirectExchange("paymentOrderStatus");
        DirectExchange warehouseCommandExchange = new DirectExchange("warehouseCommandExchange");
        Queue paymentStatus = new Queue("paymentStatus", false);
        Queue warehouseCommand = new Queue("warehouseCommand", false);

        return new Declarables(
                paymentStatus,
                warehouseCommand,
                warehouseCommandExchange,
                statusExchange,
                paymentOrderStatus,
                BindingBuilder.bind(paymentStatus).to(paymentOrderStatus).with("payStatus"),
                BindingBuilder.bind(warehouseCommand).to(warehouseCommandExchange).with("whcKey")
        );
    }
}
