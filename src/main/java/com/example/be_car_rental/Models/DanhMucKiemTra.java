package com.example.be_car_rental.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "danh_muc_kiem_tra")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DanhMucKiemTra {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "iddanhmuckiemtra")
    private int idDanhMucKiemTra;

    @Column(name = "muckiemtra")
    private String mucKiemTra;

    @Column(name = "trangthai")
    private String trangThai;

    @Column(name = "ghichu")
    private String ghiChu;

    @Column(name = "chiphiphatsinh")
    private BigDecimal chiPhiPhatSinh;

    @Column(name = "idbienbannhan")
    private int idBienBanNhan;

}