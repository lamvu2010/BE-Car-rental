package com.example.be_car_rental.DTO;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Xedto {
    private int idXe;
    private int namSanXuat;
    private int soChoNgoi;
    private Date dangKiem;
    private String bienSoXe;
    private int idDongXe;
    private String trangThai;
    private String xuatXu;
    private String kieuDang;
    private String hopSo;
    private String nhienLieu;
    private String dongCo;
    private String mauNoiThat;
    private String mauNgoaiThat;
    private String danDong;
    private String thongTinMoTa;
    private BigDecimal giaHienTai;
    private BigDecimal phiVuotGioiHan;
    private BigDecimal phiQuaGio;
    private BigDecimal phiVeSinh;
    private BigDecimal phiKhuMui;
    private int kmGioiHan;
    private int gioiHanPhiTheoGio;
}