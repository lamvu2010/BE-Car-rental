package com.example.be_car_rental.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Entity
@Table(name = "bienbangiao")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Bienbangiao {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "idbienbangiao")
    private int idbienbangiao;

    @Column(name = "ngaygiao")
    private Timestamp ngaygiao;

    @Column(name = "sokmhientai")
    private int sokmhientai;

    @Column(name = "tinhtrangxe")
    private String tinhtrangxe;

    @Column(name = "idhopdong")
    private int idhopdong;

    @Column(name = "phantramxang")
    private int phantramxang;

    @Column(name = "xang")
    private int xangtheolit;

    @Column(name = "diadiemgiao")
    private String diadiemgiao;

    @Column(name = "nguoigiaoxe")
    private String nguoigiaoxe;

    @Column(name = "phukienkemtheo")
    private String phukienkemtheo;
}