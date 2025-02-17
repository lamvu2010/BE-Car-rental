package com.example.be_car_rental.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "phu_phi_phat_sinh")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PhuPhiPhatSinh {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idphuphiphatsinh")
    private int idPhuPhiPhatSinh;

    @Column(name = "phivuotgioihan")
    private BigDecimal phiVuotGioiHan;

    @Column(name = "phiquagio")
    private BigDecimal phiQuaGio;

    @Column(name = "phivesinh")
    private BigDecimal phiVeSinh;

    @Column(name = "phikhumui")
    private BigDecimal phiKhuMui;

    @Column(name = "kmgioihan")
    private int kmGioiHan;

    @Column(name = "gioihanphitheogio")
    private int gioiHanPhiTheoGio;

    @Column(name = "idxe")
    private int idXe;

}
