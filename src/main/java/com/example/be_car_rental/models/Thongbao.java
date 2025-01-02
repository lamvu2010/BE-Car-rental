package com.example.be_car_rental.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Entity
@Table(name = "thongbao")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Thongbao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idthongbao")
    private long idthongbao;

    @Column(name = "noidung")
    private String noidung;

    @Column(name = "loaithongbao")
    private String loaithongbao;

    @Column(name = "idnoidung")
    private int idnoidung;

    @Column(name = "sodienthoai")
    private String sodienthoai;

    @Column(name = "thoigian")
    private Timestamp thoigian;

    @Column(name = "trangthai")
    private String trangthai;
}
