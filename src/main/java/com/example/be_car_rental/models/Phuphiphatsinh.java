package com.example.be_car_rental.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "phuphiphatsinh")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Phuphiphatsinh {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idphuphiphatsinh")
    private int idphuphiphatsinh;

    @Column(name = "phivuotgioihan")
    private BigDecimal phivuotgioihan;

    @Column(name = "phiquagio")
    private BigDecimal phiquagio;

    @Column(name = "phivesinh")
    private BigDecimal phivesinh;

    @Column(name = "phikhumui")
    private BigDecimal phikhumui;

    @Column(name = "kmgioihan")
    private int kmgioihan;

    @Column(name = "gioihanphitheogio")
    private int gioihanphitheogio;

    @Column(name = "idxe")
    private int idxe;
}
