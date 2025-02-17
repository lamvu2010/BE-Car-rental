package com.example.be_car_rental.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChiTietDangKyDto {
    private int idXe;
    private String soDienThoai;
    private Date ngayBatDau;
    private Date ngayKetThuc;
}
