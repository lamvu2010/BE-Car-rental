package com.example.be_car_rental.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Entity
@Table(name = "thong_tin_thanh_toan")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ThongTinThanhToan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idthongtinthanhtoan")
    private long idThongTinThanhToan;

    @Column(name = "sotiencanthanhtoan")
    private BigDecimal soTienCanThanhToan;

    @Column(name = "thoigiantaothongtin")
    private Timestamp thoiGianTaoThongTin;

    @Column(name = "noidung")
    private String noiDung;

    @Column(name = "sodienthoai")
    private String soDienThoai;

    @Column(name = "idhopdong")
    private int idHopDong;

    @Column(name = "loaithanhtoan")
    private String loaiThanhToan;

    @Column(name = "trangthai")
    private String trangThai;

    @Column(name = "sotienthanhtoan")
    private BigDecimal soTienThanhToan;

    @Column(name = "motathanhtoan")
    private String moTaThanhToan;

    @Column(name = "sotknhantien")
    private String soTkNhanTien;

    @Column(name = "transactionid")
    private String transactionId;

    @Column(name = "thoigianthanhtoan")
    private Timestamp thoiGianThanhToan;

    @Column(name = "manganhangkhachhang")
    private String maNganHangKhachHang;

    @Column(name = "tennganhangkhachhang")
    private String tenNganHangKhachHang;

    @Column(name = "tentkkhachhang")
    private String tenTkKhachHang;

    @Column(name = "sotkkhachhang")
    private String soTkKhachHang;

    @Column(name = "tentkao")
    private String tenTkAo;

    @Column(name = "sotkao")
    private String soTkAo;

}
