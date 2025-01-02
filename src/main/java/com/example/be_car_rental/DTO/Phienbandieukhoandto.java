package com.example.be_car_rental.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Phienbandieukhoandto {
    private int iddieukhoan;
    private String noidung;
    private String phienban;
    private Timestamp ngaycapnhat;
}
