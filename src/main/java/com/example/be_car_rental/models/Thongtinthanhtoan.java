package com.example.be_car_rental.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Entity
@Table(name = "thongtinthanhtoan")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Thongtinthanhtoan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idthongtinthanhtoan")
    private long idthongtinthanhtoan;

    @Column(name = "sotiencanthanhtoan")
    private BigDecimal sotiencanthanhtoan;

    @Column(name = "thoigiantaothongtin")
    private Timestamp thoigiantaothongtin;

    @Column(name = "noidung")
    private String noidung;

    @Column(name = "sodienthoai")
    private String sodienthoai;

    @Column(name = "idhopdong")
    private int idhopdong;

    @Column(name = "loaithanhtoan")
    private String loaithanhtoan;

    @Column(name = "trangthai")
    private String trangthai;

    @Column(name = "sotienthanhtoan")
    private BigDecimal sotienthanhtoan;

    @Column(name = "motathanhtoan")
    private String motathanhtoan;

    @Column(name = "sotknhantien")
    private String sotknhantien;

    @Column(name = "transactionid")
    private String transactionid;

    @Column(name = "thoigianthanhtoan")
    private Timestamp thoigianthanhtoan;

    @Column(name = "manganhangkhachhang")
    private String manganhangkhachhang;

    @Column(name = "tennganhangkhachhang")
    private String tennganhangkhachhang;

    @Column(name = "tentkkhachhang")
    private String tentkkhachhang;

    @Column(name = "sotkkhachhang")
    private  String sotkkhachhang;

    @Column(name = "tentkao")
    private String tentkao;

    @Column(name = "sotkao")
    private String sotkao;
}
