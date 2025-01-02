package com.example.be_car_rental.Configurations;

import com.twilio.Twilio;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TwilioConfig {

    @Value("${twilio.account.sid}")
    private  String accountSid;

    @Value("${twilio.auth.token}")
    private  String authToken;

    // Constructor
    @PostConstruct
    public void TwilioConfig() {
        if (accountSid == null || authToken == null) {
            throw new IllegalArgumentException("Twilio Account SID or Auth Token is null. Please check your configuration.");
        }

        try {
            // Khởi tạo Twilio
            Twilio.init(accountSid, authToken);
        } catch (Exception e) {
            // Log lỗi nếu có
            System.err.println("Error initializing Twilio: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
