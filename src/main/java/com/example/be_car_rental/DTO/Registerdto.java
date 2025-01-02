package com.example.be_car_rental.DTO;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Registerdto {
    private String sodienthoai;
    private String matkhau;
    private String hovaten;
    private String cccd;
    private String email;
    private Date ngaysinh;
    private String otp;
}
