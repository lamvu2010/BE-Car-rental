package com.example.be_car_rental.controller;

import com.example.be_car_rental.DTO.ApiResponse;
import com.example.be_car_rental.DTO.Dieukhoandto;
import com.example.be_car_rental.services.DieukhoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/dieukhoan")
public class DieukhoanController {

    @Autowired
    private DieukhoanService dieukhoanService;

    @GetMapping
    public ResponseEntity<ApiResponse> getAll() {
        ApiResponse response = dieukhoanService.getAll();
        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
    }

    @PostMapping
    public ResponseEntity<ApiResponse> insert(@RequestBody Dieukhoandto dieukhoandto) {
        ApiResponse response = dieukhoanService.insert(dieukhoandto);
        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> delete(@PathVariable int id) {
        ApiResponse response = dieukhoanService.delete(id);
        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @GetMapping("/hasDetail")
    public ResponseEntity<ApiResponse> hasDetail() {
        ApiResponse response = dieukhoanService.hasDetailTerm();
        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @GetMapping("/getDetail/{id}")
    public ResponseEntity<ApiResponse> getDetail(@PathVariable int id) {
        ApiResponse response = dieukhoanService.getDetail(id, true);
        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @GetMapping("/getVersions/{id}")
    public ResponseEntity<ApiResponse> getVersions(@PathVariable int id) {
        ApiResponse response = dieukhoanService.getVersions(id);
        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @GetMapping("/getTitle/{id}")
    public ResponseEntity<ApiResponse> getTitle(@PathVariable int id) {
        ApiResponse response = dieukhoanService.getTermTitle(id);
        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @GetMapping("/getDetail/{iddieukhoan}/{version}")
    public ResponseEntity<ApiResponse> getDetailByVersion(@PathVariable int iddieukhoan,
                                                          @PathVariable String version) {
        ApiResponse response = dieukhoanService.getDetailByIdAndVersion(iddieukhoan, version);
        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @GetMapping("/ds/noidung")
    ResponseEntity<ApiResponse> ds_dieukhoan_dangapdung(){
        ApiResponse response = dieukhoanService.ds_dieukhoan_dangapdung();
        if(response.isSuccess()){
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
}