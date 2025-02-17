package com.example.be_car_rental.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ThongTinHuyCocDto {
    private int idHopDong;
    private String liDo;
    private String phuongThuc;
    private String nganHang;
    private String soTaiKhoan;
}
