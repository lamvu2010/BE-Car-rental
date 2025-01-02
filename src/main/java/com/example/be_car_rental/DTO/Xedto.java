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
    private int idxe;
    private int namsanxuat;
    private int sochongoi;
    private Date dangkiem;
    private String biensoxe;
    private int iddongxe;
    private String trangthai;
    private String xuatxu;
    private String kieudang;
    private String hopso;
    private String nhienlieu;
    private String dongco;
    private String maunoithat;
    private String maungoaithat;
    private String dandong;
    private String thongtinmota;
    private BigDecimal giahientai;
    private BigDecimal phivuotgioihan;
    private BigDecimal phiquagio;
    private BigDecimal phivesinh;
    private BigDecimal phikhumui;
    private int kmgioihan;
    private int gioihanphitheogio;
}