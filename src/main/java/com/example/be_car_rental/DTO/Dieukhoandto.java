package com.example.be_car_rental.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Dieukhoandto {
    private int iddieukhoan;
    private String tieude;
    private Date ngaytao;
}
