package com.example.be_car_rental.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Entity
@Table(name = "thong_tin_huy_coc")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ThongTinHuyCoc {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idthongtinhuycoc")
    private int idThongTinHuyCoc;

    @Column(name = "thoigianhuycoc")
    private Timestamp thoiGianHuyCoc;

    @Column(name = "sotienhoanlai")
    private BigDecimal soTienHoanLai;

    @Column(name = "lidohuycoc")
    private String liDoHuyCoc;

    @Column(name = "trangthaihoantien")
    private String trangThaiHoanTien;

    @Column(name = "idhopdong")
    private int idHopDong;

    @Column(name = "phuongthuc")
    private String phuongThuc;

    @Column(name = "nganhang")
    private String nganHang;

    @Column(name = "sotaikhoan")
    private String soTaiKhoan;

}
