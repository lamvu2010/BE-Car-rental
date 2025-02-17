package com.example.be_car_rental.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BienBanGiaoDto {
    private Timestamp ngayGiao;
    private int soKmHienTai;
    private String tinhTrangXe;
    private int idHopDong;
    private int phanTramXang;
    private int xangTheoLit;
    private String diaDiemGiao;
    private String nguoiGiaoXe;
    private String phuKienKemTheo;
}
