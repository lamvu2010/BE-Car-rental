package com.example.be_car_rental.Controller;

import com.example.be_car_rental.DTO.ApiResponse;
import com.example.be_car_rental.DTO.DongXeDto;
import com.example.be_car_rental.Services.DongXeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/dongxe")
public class DongXeController {
    @Autowired
    private DongXeService dongxeService;

    @GetMapping
    public ResponseEntity<ApiResponse> getAll(){
        ApiResponse response = dongxeService.getAll();
        if (response.isSuccess()){
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getDongxeByIdHangXe(@PathVariable int id){
        ApiResponse response = dongxeService.getDongXeByIdHangXe(id);
        if (response.isSuccess()){
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
    }

    @PostMapping
    public ResponseEntity<ApiResponse> insert(@RequestBody DongXeDto dongxedto){
        ApiResponse response = dongxeService.insert(dongxedto);
        if(response.isSuccess()){
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @PutMapping
    public ResponseEntity<ApiResponse> update(@RequestBody DongXeDto dongxedto){
        ApiResponse response = dongxeService.update((dongxedto));
        if(response.isSuccess()){
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> delete(@PathVariable int id){
        ApiResponse response = dongxeService.delete(id);
        if (response.isSuccess()){
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
}