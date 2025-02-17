package com.example.be_car_rental.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DongXeDto {
    private int idDongXe;
    private String tenDongXe;
    private int idHangXe;
}
