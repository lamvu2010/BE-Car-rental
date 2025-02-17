package com.example.be_car_rental.Controller;

import com.example.be_car_rental.DTO.ApiResponse;
import com.example.be_car_rental.DTO.ThanhToanHoanTatDto;
import com.example.be_car_rental.Services.PaymentService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @GetMapping("/datcoc/{idhopdong}")
    ResponseEntity<ApiResponse> createdLinkPaymentDatCoc(@PathVariable int idhopdong) throws Exception {
        ApiResponse response = paymentService.createPaymentLinkDatCoc(idhopdong);
        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @PostMapping("/thanhtoan/hoantat")
    ResponseEntity<ApiResponse> createdLinkPaymentThanhToanHoanTat(@RequestBody ThanhToanHoanTatDto thanhtoanhoantatdto) throws Exception {
        ApiResponse response = paymentService.createPaymentLinkHoanTat(thanhtoanhoantatdto);
        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
    @PostMapping("/webhook")
    ResponseEntity<ApiResponse> recivedWebhook(@RequestBody ObjectNode body) throws JsonProcessingException {
        try {
            ApiResponse response = paymentService.handleWebhook(body);
            if (response.isSuccess()) {
                return ResponseEntity.ok(response); // HTTP 200
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response); // HTTP 400
        } catch (Exception ex) {
            System.out.println("Error handling webhook: " + ex);
            ApiResponse errorResponse = new ApiResponse<>(false, "Server error", null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse); // HTTP 500
        }
    }
}
