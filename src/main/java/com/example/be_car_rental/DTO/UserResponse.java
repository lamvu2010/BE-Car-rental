package com.example.be_car_rental.DTO;

import com.example.be_car_rental.Models.KhachHang;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
    private KhachHang khachHang;
    private String quyen;
    private String token;
    private boolean giayPhepLaiXe;
}
