package com.AthorizationAndAuthentication.AthorizationAndAuthentication.rabbitMQ;

import com.AthorizationAndAuthentication.AthorizationAndAuthentication.service.UserService;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

@Configuration
public class ReceiverClass {
    

    @Autowired
    UserService userService;
    

    @RabbitListener(queues = "auth")
    public void receiveMessage(Long number){
        System.out.println("POGODIO JE AUTHHHHHHHH RECEIVER-A");
        userService.increaseEndUsersNumberOfAds();
        
    }
}