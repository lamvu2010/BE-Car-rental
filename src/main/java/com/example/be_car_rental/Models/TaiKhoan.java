package com.example.be_car_rental.Models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Entity
@Table(name = "tai_khoan")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaiKhoan {
    @Id
    @Column(name = "sodienthoai")
    private String soDienThoai;

    @Column(name = "matkhau")
    private String matKhau;

    @Column(name = "otp")
    private String otp;

    @Column(name = "thoigiantaoma")
    private Timestamp thoiGianTaoMa;

    @Column(name = "thoigianhethan")
    private Timestamp thoiGianHetHan;

    @Column(name = "trangthai")
    private Boolean trangThai;

    @Column(name = "idquyen")
    private int idQuyen;


}
