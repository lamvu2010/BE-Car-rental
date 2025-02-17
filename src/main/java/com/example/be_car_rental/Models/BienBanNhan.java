package com.example.be_car_rental.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Entity
@Table(name = "bien_ban_nhan")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BienBanNhan {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "idbienbannhan")
    private int idBienBanNhan;

    @Column(name = "ngaynhan")
    private Timestamp ngayNhan;

    @Column(name = "sokmhientai")
    private int soKmHienTai;

    @Column(name = "tinhtrangxe")
    private String tinhTrangXe;

    @Column(name = "idhopdong")
    private int idHopDong;

    @Column(name = "phantramxang")
    private int phanTramXang;

    @Column(name = "xangtheolit")
    private int xangTheoLit;

    @Column(name = "phukienkemtheo")
    private String phuKienKemTheo;

    @Column(name = "nguoinhanxe")
    private String nguoiNhanXe;

    @Column(name = "diadiemnhan")
    private String diaDiemNhan;

}
