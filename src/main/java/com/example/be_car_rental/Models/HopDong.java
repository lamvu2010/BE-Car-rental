package com.example.be_car_rental.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Entity
@Table(name = "hop_dong")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HopDong {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "idhopdong")
    private int idHopDong;

    @Column(name = "tiendatcoc")
    private BigDecimal tienDatCoc;

    @Column(name = "giathuetong")
    private BigDecimal giaThueTong;

    @Column(name = "sodienthoai")
    private String soDienThoai;

    @Column(name = "idxe")
    private int idXe;

    @Column(name = "thoigianbatdau")
    private Timestamp thoiGianBatDau;

    @Column(name = "thoigianketthuc")
    private Timestamp thoiGianKetThuc;

    @Column(name = "giathuemotngay")
    private BigDecimal giaThueMotNgay;

    @Column(name = "songaythue")
    private int soNgayThue;

    @Column(name = "ngaytao")
    private Timestamp ngayTao;

    @Column(name = "phutroiquangduong")
    private BigDecimal phuTroiQuangDuong;

    @Column(name = "sokmgioihan")
    private int soKmGioiHan;

    @Column(name = "phutroithoigian")
    private BigDecimal phuTroiThoiGian;

    @Column(name = "sogiothue")
    private int soGioThue;

    @Column(name = "trangthai")
    private String trangThai;

    @Column(name = "gioihanphitheogio")
    private int gioiHanPhiTheoGio;

    @Column(name = "thoigianduyet")
    private Timestamp thoiGianDuyet;

    @Column(name = "ghichu")
    private String ghiChu;

    @Column(name = "thoigianhuy")
    private Timestamp thoiGianHuy;

    @Column(name = "thoigiandatcoc")
    private Timestamp thoiGianDatCoc;

    @Column(name = "phivesinh")
    private BigDecimal phiVeSinh;

    @Column(name = "phikhumui")
    private BigDecimal phiKhuMui;

    @Column(name = "thoigianhethan")
    private Timestamp thoiGianHetHan;

    @Column(name = "thoigianhoantien")
    private Timestamp thoiGianHoanTien;

    @Column(name = "thoigiandanhgia")
    private Timestamp thoiGianDanhGia;

    @Column(name = "sokmgioihanchuyendi")
    private int soKmGioiHanChuyenDi;

    @Column(name = "thoigianhoantat")
    private Timestamp thoiGianHoanTat;

}