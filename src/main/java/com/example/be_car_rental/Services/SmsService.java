package com.example.be_car_rental.Services;

import com.twilio.rest.api.v2010.account.Message;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class SmsService {
    @Value("${twilio.phone.number}")
    private String twilioPhoneNumber;

    public void sendOtp(String phoneNumber, String otp) {
        try {
            Message message = Message.creator(
                            new com.twilio.type.PhoneNumber("whatsapp:+84917683801"),
                            new com.twilio.type.PhoneNumber("whatsapp:+14155238886"),
                            "Mã Otp của bạn là: " + otp)
                    .create();
            System.out.println("SMS sent: " + message.getSid());
            System.out.println("Message status: " + message.getStatus());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}