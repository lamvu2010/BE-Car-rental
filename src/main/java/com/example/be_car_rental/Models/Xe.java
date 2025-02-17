package com.example.be_car_rental.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Date;

@Entity
@Table(name = "xe")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Xe {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "idxe")
    private int idXe;

    @Column(name = "namsanxuat")
    private int namSanXuat;

    @Column(name = "sochongoi")
    private int soChoNgoi;

    @Column(name = "dangkiem")
    private Date dangKiem;

    @Column(name = "biensoxe")
    private String bienSoXe;

    @Column(name = "iddongxe")
    private int idDongXe;

    @Column(name = "trangthai")
    private String trangThai;

    @Column(name = "xuatxu")
    private String xuatXu;

    @Column(name = "kieudang")
    private String kieuDang;

    @Column(name = "hopso")
    private String hopSo;

    @Column(name = "nhienlieu")
    private String nhienLieu;

    @Column(name = "dongco")
    private String dongCo;

    @Column(name = "maungoaithat")
    private String mauNgoaiThat;

    @Column(name = "maunoithat")
    private String mauNoiThat;

    @Column(name = "dandong")
    private String danDong;

    @Column(name = "thongtinmota")
    private String thongTinMoTa;

    @Column(name = "giahientai")
    private BigDecimal giaHienTai;

}