package com.example.be_car_rental.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;

@Entity
@Table(name = "hopdong")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Hopdong {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "idhopdong")
    private int idhopdong;

    @Column(name = "tiendatcoc")
    private BigDecimal tiendatcoc;

    @Column(name = "giathuetong")
    private BigDecimal giathuetong;

    @Column(name = "sodienthoai")
    private String sodienthoai;

    @Column(name = "idxe")
    private int idxe;

    @Column(name = "thoigianbatdau")
    private Timestamp thoigianbatdau;

    @Column(name = "thoigianketthuc")
    private Timestamp thoigianketthuc;

    @Column(name = "giathuemotngay")
    private BigDecimal giathuemotngay;

    @Column(name = "songaythue")
    private int songaythue;

    @Column(name = "ngaytao")
    private Timestamp ngaytao;

    @Column(name = "phutroiquangduong")
    private BigDecimal phutroiquangduong;

    @Column(name = "sokmgioihan")
    private int sokmgioihan;

    @Column(name = "phutroithoigian")
    private BigDecimal phutroithoigian;

    @Column(name = "sogiothue")
    private int sogiothue;

    @Column(name = "trangthai")
    private String trangthai;

    @Column(name = "gioihanphitheogio")
    private int gioihanphitheogio;

    @Column(name = "thoigianduyet")
    private Timestamp thoigianduyet;

    @Column(name = "ghichu")
    private String ghichu;

    @Column(name = "thoigianhuy")
    private Timestamp thoigianhuy;

    @Column(name = "thoigiandatcoc")
    private Timestamp thoigiandatcoc;

    @Column(name = "phivesinh")
    private BigDecimal phivesinh;

    @Column(name = "phikhumui")
    private BigDecimal phikhumui;

    @Column(name = "thoigianhethan")
    private Timestamp thoigianhethan;

    @Column(name = "thoigianhoantien")
    private Timestamp thoigianhoantien;

    @Column(name = "thoigiandanhgia")
    private Timestamp thoigiandanhgia;

    @Column(name = "sokmgioihanchuyendi")
    private int sokmgioihanchuyendi;

    @Column(name = "thoigianhoantat")
    private Timestamp thoigianhoantat;
}