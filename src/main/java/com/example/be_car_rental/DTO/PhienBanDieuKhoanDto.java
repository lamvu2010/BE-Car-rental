package com.example.be_car_rental.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PhienBanDieuKhoanDto {
    private int idDieuKhoan;
    private String noiDung;
    private String phienBan;
    private Timestamp ngayCapNhat;

}
