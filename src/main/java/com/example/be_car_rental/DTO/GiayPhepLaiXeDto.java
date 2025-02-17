package com.example.be_car_rental.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GiayPhepLaiXeDto {
    private int idGiayPhepLaiXe;
    private String soGiayPhepLaiXe;
    private String hoVaTen;
    private String soDienThoai;
    private boolean trangThai;
    private Date ngaySinh;

}
