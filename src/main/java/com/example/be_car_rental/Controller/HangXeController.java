package com.example.be_car_rental.Controller;

import com.example.be_car_rental.DTO.ApiResponse;
import com.example.be_car_rental.DTO.HangXeDto;
import com.example.be_car_rental.Services.HangXeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/hangxe")
public class HangXeController {
    @Autowired
    private HangXeService hangxeService;

    @GetMapping
    public ResponseEntity<ApiResponse> getAll(){
        ApiResponse response = hangxeService.getAll();
        if(response.isSuccess()){
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
    }

    @PostMapping
    public ResponseEntity<ApiResponse> insert(@RequestBody HangXeDto hangxedto){
        ApiResponse response = hangxeService.insert(hangxedto);
        if(response.isSuccess()){
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @PutMapping
    public ResponseEntity<ApiResponse> update(@RequestBody HangXeDto hangxedto){
        ApiResponse response = hangxeService.update(hangxedto);
        if(response.isSuccess()){
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> delete(@PathVariable int id){
        ApiResponse response = hangxeService.delete(id);
        if(response.isSuccess()){
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
}
