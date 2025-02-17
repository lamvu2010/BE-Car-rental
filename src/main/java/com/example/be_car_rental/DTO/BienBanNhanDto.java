package com.example.be_car_rental.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BienBanNhanDto {
    private Timestamp ngayNhan;
    private int soKmHienTai;
    private String tinhTrangXe;
    private int idHopDong;
    private int phanTramXang;
    private int xangTheoLit;
    private String phuKienKemTheo;
    private String nguoiNhanXe;
    private String diaDiemNhan;
    private List<Map<String, Object>> danhMuc;
}
