package com.example.be_car_rental.DTO;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Gplxdto {
    private int idgiaypheplaixe;
    private String sogplx;
    private String hovaten;
    private String sodienthoai;
    private boolean trangthai;
    private Date ngaysinh;
}
