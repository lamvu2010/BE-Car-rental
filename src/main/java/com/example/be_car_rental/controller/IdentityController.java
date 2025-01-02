package com.example.be_car_rental.controller;

import com.example.be_car_rental.DTO.ApiResponse;
import com.example.be_car_rental.DTO.Gplxdto;
import com.example.be_car_rental.services.IdentityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/identity")
public class IdentityController {

    @Autowired
    private IdentityService identityService;

    @PostMapping("/add-gplx")
    public ResponseEntity<ApiResponse> insertGPLX(@RequestBody Gplxdto gplxdto){
        ApiResponse response = identityService.verifyDrivingCard(gplxdto);
        if(response.isSuccess()){
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @PostMapping("/gplx")
    public ResponseEntity<ApiResponse> getThongTinGPLX(@RequestBody Gplxdto gplxdto){
        ApiResponse response = identityService.getThongTinGPLX(gplxdto.getSodienthoai());
        if(response.isSuccess()){
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
}
