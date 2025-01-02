package com.example.be_car_rental.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Ctdkdto {
    private int idxe;
    private String sdt;
    private Date ngaybatdau;
    private Date ngayketthuc;
}
