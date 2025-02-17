package com.example.be_car_rental.DTO;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class BangGiaDto {
    private int idBangGia;
    private Timestamp thoiGianBatDau;
    private Timestamp thoiGianKetThuc;
    private Boolean trangThai;
    private String noiDung;
}
