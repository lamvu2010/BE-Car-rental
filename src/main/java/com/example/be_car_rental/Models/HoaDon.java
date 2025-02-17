package com.example.be_car_rental.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HoaDon {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "idhoadon")
    private int idHoaDon;

    @Column(name = "tienthue")
    private BigDecimal tienThue;

    @Column(name = "tongtien")
    private BigDecimal tongTien;

    @Column(name = "tiendacoc")
    private BigDecimal tienDaCoc;

    @Column(name = "tienconlai")
    private BigDecimal tienConLai;

    @Column(name = "tienphatsinh")
    private BigDecimal tienPhatSinh;

    @Column(name = "idhopdong")
    private int idHopDong;

}
