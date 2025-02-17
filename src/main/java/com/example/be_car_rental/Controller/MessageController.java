package com.example.be_car_rental.Controller;

import com.example.be_car_rental.Models.ThongBao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class MessageController {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    public void send(String sodienthoai, ThongBao thongbao){
        messagingTemplate.convertAndSend("/topic/" + sodienthoai, thongbao);
    }
}