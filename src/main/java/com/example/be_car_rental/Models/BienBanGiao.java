package com.example.be_car_rental.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Entity
@Table(name = "bien_ban_giao")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BienBanGiao {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "idbienbangiao")
    private int idBienBanGiao;

    @Column(name = "ngaygiao")
    private Timestamp ngayGiao;

    @Column(name = "sokmhientai")
    private int soKmHienTai;

    @Column(name = "tinhtrangxe")
    private String tinhTrangXe;

    @Column(name = "idhopdong")
    private int idHopDong;

    @Column(name = "phantramxang")
    private int phanTramXang;

    @Column(name = "xang")
    private int xangTheoLit;

    @Column(name = "diadiemgiao")
    private String diaDiemGiao;

    @Column(name = "nguoigiaoxe")
    private String nguoiGiaoXe;

    @Column(name = "phukienkemtheo")
    private String phuKienKemTheo;

}