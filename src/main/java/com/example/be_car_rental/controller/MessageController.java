package com.example.be_car_rental.controller;

import com.example.be_car_rental.models.Thongbao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class MessageController {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    public void send(String sodienthoai, Thongbao thongbao){
        messagingTemplate.convertAndSend("/topic/" + sodienthoai, thongbao);
    }
}