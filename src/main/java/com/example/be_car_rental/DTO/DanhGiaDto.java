package com.example.be_car_rental.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DanhGiaDto {
    private String soDienThoai;
    private int idXe;
    private int idHopDong;
    private String danhGia;
    private int sao;

}
