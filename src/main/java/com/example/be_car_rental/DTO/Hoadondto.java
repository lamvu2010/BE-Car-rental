package com.example.be_car_rental.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Hoadondto {
    private BigDecimal tienthue;
    private BigDecimal tiendatcoc;
    private BigDecimal tienconlai;
    private BigDecimal tienphatsinh;
    private BigDecimal tongtien;
    private int idhopdong;
}
