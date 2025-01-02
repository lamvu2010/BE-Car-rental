package com.example.be_car_rental.Schedule;

import com.example.be_car_rental.services.HopdongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class Scheduler {

    @Autowired
    private HopdongService hopdongService;

    @Scheduled(cron = "0 0/30 * * * ?")
    public void kiemTraHopDongHetHan() {
        System.out.println("Đang kiểm tra trạng thái hợp đồng...");
        hopdongService.capNhatHopDongHetHan();
    }
}