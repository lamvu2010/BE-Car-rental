package com.example.be_car_rental.Controller;

import com.example.be_car_rental.DTO.ApiResponse;
import com.example.be_car_rental.Services.ThongBaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/thongbao")
public class ThongBaoController {

    @Autowired
    private ThongBaoService thongbaoService;

    @GetMapping
    public ResponseEntity<ApiResponse> getThongBao(@RequestParam int page, @RequestParam int size, @RequestParam String sdt){
        ApiResponse response = thongbaoService.findAll(page,size,sdt);
        if(response.isSuccess()){
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @GetMapping("/daxem/{id}")
    public ResponseEntity<ApiResponse> updateThongBao(@PathVariable long id){
        ApiResponse response = thongbaoService.setThongBaoThanhDaDoc(id);
        if(response.isSuccess()){
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
}
