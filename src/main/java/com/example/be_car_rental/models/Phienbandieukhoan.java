package com.example.be_car_rental.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Entity
@Table(name = "phienbandieukhoan")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Phienbandieukhoan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idphienbandieukhoan")
    private int idphienbandieukhoan;

    @Column(name = "noidung")
    private String noidung;

    @Column(name = "phienban")
    private String phienban;

    @Column(name = "trangthai")
    private Boolean trangthai;

    @Column(name = "iddieukhoan")
    private int iddieukhoan;

    @Column(name = "ngaycapnhat")
    private Timestamp ngaycapnhat;
}