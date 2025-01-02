package com.example.be_car_rental.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Ctbanggiadto {
    private int idchitietbanggia;
    private int idbanggia;
    private int iddongxe;
    private BigDecimal giathue;
}
