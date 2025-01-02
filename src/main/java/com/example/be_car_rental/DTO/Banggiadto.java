package com.example.be_car_rental.DTO;

import lombok.Data;

import java.sql.Date;
import java.sql.Timestamp;

@Data
public class Banggiadto {
    private int idbanggia;
    private Timestamp thoigianbatdau;
    private Timestamp thoigianketthuc;
    private Boolean trangthai;
    private String noidung;
}
