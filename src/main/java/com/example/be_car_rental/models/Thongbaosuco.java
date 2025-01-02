package com.example.be_car_rental.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "thongbaosuco")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Thongbaosuco {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idthongbaosuco")
    private int idthongbaosuco;

    @Column(name = "vitri")
    private String vitri;

    @Column(name = "lienlac")
    private String lienlac;

    @Column(name = "mota")
    private String mota;

    @Column(name = "idhopdong")
    private int idhopdong;
}