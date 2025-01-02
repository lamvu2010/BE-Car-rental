package com.example.be_car_rental.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Thanhtoanhoantatdto {
    private BigDecimal sotienthanhtoan;
    private int idhopdong;
}