package com.example.be_car_rental.Controller;

import com.example.be_car_rental.DTO.ApiResponse;
import com.example.be_car_rental.DTO.Xedto;
import com.example.be_car_rental.Services.XeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/xe")
public class XeController {
    @Autowired
    private XeService xeService;

//    @GetMapping
//    public ResponseEntity<ApiResponse> getAll(){
//        ApiResponse response = xeService.getAll();
//        if(response.isSuccess()){
//            return ResponseEntity.ok(response);
//        }
//        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
//    }

    @GetMapping
    public ResponseEntity<ApiResponse> getPage(@RequestParam(value = "page", defaultValue = "0") int page, @RequestParam(value = "size", defaultValue = "5") int size) {
        ApiResponse response = xeService.getPage(page, size);
        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
    }

    @PostMapping
    public ResponseEntity<ApiResponse> insert(@RequestBody Xedto xedto) {
        ApiResponse response = xeService.insert(xedto);
        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @PutMapping
    public ResponseEntity<ApiResponse> update(@RequestBody Xedto xedto) {
        ApiResponse response = xeService.update(xedto);
        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> delete(@PathVariable int id) {
        ApiResponse response = xeService.delete(id);
        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> findById(@PathVariable int id) {
        ApiResponse response = xeService.findById(id);
        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
    }

    @GetMapping("/ds-cho-thue")
    public ResponseEntity<ApiResponse> getDsXeChoThue() {
        ApiResponse response = xeService.dsXeChoThue();
        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
    }

//    @GetMapping("/bienso/{id}")
//    public ResponseEntity<ApiResponse> dsBienSoTheoIddongxe(@PathVariable int id) {
//        ApiResponse response = xeService.da(id);
//        if (response.isSuccess()) {
//            return ResponseEntity.ok(response);
//        }
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
//    }

    @GetMapping("/sl")
    public ResponseEntity<ApiResponse> slXe() {
        ApiResponse response = xeService.count();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/ds-xe-de-xuat")
    public ResponseEntity<ApiResponse> dsXeDeXuat() {
        ApiResponse response = xeService.getDsXeDeXuatTrangHome();
        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @GetMapping("/danhgia")
    public ResponseEntity<ApiResponse> layDanhsachdanhgia(@RequestParam int idxe,
                                                          @RequestParam int page,
                                                          @RequestParam int pageSize) {
        ApiResponse response = xeService.layDanhsachdanhgia(idxe, page, pageSize);
        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
}
