package com.example.be_car_rental.Controller;

import com.example.be_car_rental.DTO.ApiResponse;
import com.example.be_car_rental.DTO.PhienBanDieuKhoanDto;
import com.example.be_car_rental.Services.DieuKhoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/phienbandieukhoan")
public class PhienBanDieuKhoanController {
    @Autowired
    private DieuKhoanService dieukhoanService;

    @PostMapping
    public ResponseEntity<ApiResponse> insert(@RequestBody PhienBanDieuKhoanDto dto){
        ApiResponse response = dieukhoanService.insertPhienBanDieuKhoan(dto);
        if(response.isSuccess()){
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
}
