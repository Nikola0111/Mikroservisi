package com.Booking.Booking.rabbitMQ;

import com.Booking.Booking.service.ShoppingCartService;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

@Configuration
public class ReceiverClass {
    

    @Autowired
    ShoppingCartService shoppingCartService;
    

    public void handleMessage(final Long id){
        System.out.println("POGODIO JE BOOKING");
        System.out.println("ID USERA jeEEEEEEEEEEE="+id );

        shoppingCartService.save(id);

    }
}