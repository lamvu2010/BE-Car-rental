package com.example.be_car_rental.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Entity
@Table(name = "phien_ban_dieu_khoan")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PhienBanDieuKhoan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idphienbandieukhoan")
    private int idPhienBanDieuKhoan;

    @Column(name = "noidung")
    private String noiDung;

    @Column(name = "phienban")
    private String phienBan;

    @Column(name = "trangthai")
    private Boolean trangThai;

    @Column(name = "iddieukhoan")
    private int idDieuKhoan;

    @Column(name = "ngaycapnhat")
    private Timestamp ngayCapNhat;

}