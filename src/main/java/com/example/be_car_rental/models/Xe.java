package com.example.be_car_rental.models;

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
    private int idxe;

    @Column(name = "namsanxuat")
    private int namsanxuat;

    @Column(name = "sochongoi")
    private int sochongoi;

    @Column(name = "dangkiem")
    private Date dangkiem;

    @Column(name = "biensoxe")
    private String biensoxe;

    @Column(name = "iddongxe")
    private int iddongxe;

    @Column(name = "trangthai")
    private String trangthai;

    @Column(name = "xuatxu")
    private String xuatxu;

    @Column(name = "kieudang")
    private String kieudang;

    @Column(name = "hopso")
    private String hopso;

    @Column(name = "nhienlieu")
    private String nhienlieu;

    @Column(name = "dongco")
    private String dongco;

    @Column(name = "maungoaithat")
    private String maungoaithat;

    @Column(name = "maunoithat")
    private String maunoithat;


    @Column(name = "dandong")
    private String dandong;

    @Column(name = "thongtinmota")
    private String thongtinmota;

    @Column(name = "giahientai")
    private BigDecimal giahientai;
}