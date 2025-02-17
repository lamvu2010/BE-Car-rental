package com.example.be_car_rental.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HoaDonDto {
    private BigDecimal tienThue;
    private BigDecimal tienDatCoc;
    private BigDecimal tienConLai;
    private BigDecimal tienPhatSinh;
    private BigDecimal tongTien;
    private int idHopDong;

}
