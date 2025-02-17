package com.example.be_car_rental.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HopDongDto {
    private int idHopDong;
    private BigDecimal tienDatCoc;
    private BigDecimal giaThueTong;
    private String soDienThoai;
    private int idXe;
    private Timestamp thoiGianBatDau;
    private Timestamp thoiGianKetThuc;
    private BigDecimal giaThueMotNgay;
    private int soNgayThue;
    private Timestamp ngayTao;
    private BigDecimal phuTroiQuangDuong;
    private int soKmGioiHan;
    private int soKmGioiHanChuyenDi;
    private BigDecimal phuTroiThoiGian;
    private int gioiHanPhiTheoGio;
    private int soGioThue;
    private String trangThai;
    private BigDecimal phiVeSinh;
    private BigDecimal phiKhuiMui;
}
