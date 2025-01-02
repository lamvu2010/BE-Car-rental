package com.example.be_car_rental.controller;

import com.example.be_car_rental.DTO.ApiResponse;
import com.example.be_car_rental.DTO.Logindto;
import com.example.be_car_rental.DTO.Registerdto;
import com.example.be_car_rental.services.IdentityService;
import com.example.be_car_rental.services.SmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/identity")
public class LoginController {

    @Autowired
    private IdentityService identityService;

    @Autowired
    private SmsService smsService;

    @PostMapping("/register")
        public ResponseEntity<ApiResponse> register(@RequestBody Registerdto registerdto){
        ApiResponse response = identityService.register(registerdto);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/verify")
    public ResponseEntity<ApiResponse> verify(@RequestBody Registerdto registerdto){
        ApiResponse response = identityService.verifyOtp(registerdto);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse> verify(@RequestBody Logindto logindto){
        System.out.println("Login");
        ApiResponse response = identityService.login(logindto);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/sendSms")
    public ResponseEntity<ApiResponse> sendingSms(@RequestBody Map<String,String> object){
        String from =  object.get("from");
        String to = object.get("to");
        System.out.println(from);
        System.out.println(to);
        try{
            smsService.sendOtp("","123456");
            return ResponseEntity.ok(null);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
}