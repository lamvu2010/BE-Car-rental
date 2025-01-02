package com.example.be_car_rental.controller;

import com.example.be_car_rental.DTO.ApiResponse;
import com.example.be_car_rental.DTO.Phienbandieukhoandto;
import com.example.be_car_rental.services.DieukhoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/phienbandieukhoan")
public class PhienbandieukhoanController {
    @Autowired
    private DieukhoanService dieukhoanService;

    @PostMapping
    public ResponseEntity<ApiResponse> insert(@RequestBody Phienbandieukhoandto dto){
        ApiResponse response = dieukhoanService.insertPhienBanDieuKhoan(dto);
        if(response.isSuccess()){
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
}
