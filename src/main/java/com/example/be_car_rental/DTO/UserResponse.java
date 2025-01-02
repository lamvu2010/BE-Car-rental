package com.example.be_car_rental.DTO;

import com.example.be_car_rental.models.Khachhang;
import com.example.be_car_rental.models.Quyen;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
    private Khachhang khachhang;
    private String quyen;
    private String token;
    private boolean gplx;
}
