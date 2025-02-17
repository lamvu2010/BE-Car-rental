package com.example.be_car_rental.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ThongTinDangKyTaiKhoanDto {
    private String soDienThoai;
    private String matKhau;
    private String hoVaTen;
    private String cccd;
    private String email;
    private Date ngaySinh;
    private String otp;
}
