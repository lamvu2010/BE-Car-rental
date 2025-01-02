package com.example.be_car_rental.models;

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
public class Hoadon {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "idhoadon")
    private int idhoadon;

    @Column(name = "tienthue")
    private BigDecimal tienthue;

    @Column(name = "tongtien")
    private BigDecimal tongtien;

    @Column(name = "tiendacoc")
    private BigDecimal tiendacoc;

    @Column(name = "tienconlai")
    private BigDecimal tienconlai;

    @Column(name = "tienphatsinh")
    private BigDecimal tienphatsinh;

    @Column(name = "idhopdong")
    private int idhopdong;
}
