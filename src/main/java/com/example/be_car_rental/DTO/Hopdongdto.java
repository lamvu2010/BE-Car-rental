package com.example.be_car_rental.DTO;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Hopdongdto {
    private int idhopdong;
    private BigDecimal tiendatcoc;
    private BigDecimal giathuetong;
    private String sodienthoai;
    private int idxe;
    private Timestamp thoigianbatdau;
    private Timestamp thoigianketthuc;
    private BigDecimal giathuemotngay;
    private int songaythue;
    private Timestamp ngaytao;
    private BigDecimal phutroiquangduong;
    private int sokmgioihan;
    private int sokmgioihanchuyendi;
    private BigDecimal phutroithoigian;
    private int gioihanphitheogio;
    private int sogiothue;
    private String trangthai;
    private BigDecimal phivesinh;
    private BigDecimal phikhumui;
}
