package com.example.be_car_rental.Controller;

import com.example.be_car_rental.DTO.ApiResponse;
import com.example.be_car_rental.DTO.GiayPhepLaiXeDto;
import com.example.be_car_rental.Services.IdentityService;
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
    public ResponseEntity<ApiResponse> insertGPLX(@RequestBody GiayPhepLaiXeDto giayPhepLaiXeDto){
        ApiResponse response = identityService.verifyDrivingCard(giayPhepLaiXeDto);
        if(response.isSuccess()){
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @PostMapping("/gplx")
    public ResponseEntity<ApiResponse> getThongTinGPLX(@RequestBody GiayPhepLaiXeDto giayPhepLaiXeDto){
        ApiResponse response = identityService.getThongTinGPLX(giayPhepLaiXeDto.getSoDienThoai());
        if(response.isSuccess()){
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
}
