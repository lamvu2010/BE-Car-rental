package com.example.be_car_rental.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Entity
@Table(name = "thong_bao")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ThongBao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idthongbao")
    private long idThongBao;

    @Column(name = "noidung")
    private String noiDung;

    @Column(name = "loaithongbao")
    private String loaiThongBao;

    @Column(name = "idnoidung")
    private int idNoiDung;

    @Column(name = "sodienthoai")
    private String soDienThoai;

    @Column(name = "thoigian")
    private Timestamp thoiGian;

    @Column(name = "trangthai")
    private String trangThai;

}
