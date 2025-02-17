package com.example.be_car_rental.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "chi_tiet_hop_dong_dieu_khoan")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChiTietHopDongDieuKhoan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idcthopdongdieukhoan")
    private int idChiTietHopDongDieuKhoan;

    @Column(name = "idhopdong")
    private int idHopDong;

    @Column(name = "idphienbandieukhoan")
    private int idPhienBanDieuKhoan;

}
