package com.podag.order;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class OrderApplication {
    private static final Logger LOGGER= LoggerFactory.getLogger(OrderApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(OrderApplication.class, args);
        LOGGER.info("Starting programm");
    }

}
