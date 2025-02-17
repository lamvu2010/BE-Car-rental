package com.example.be_car_rental.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChiTietBangGiaDto {
    private int idChiTietBangGia;
    private int idBangGia;
    private int idDongXe;
    private BigDecimal giaThue;
}
