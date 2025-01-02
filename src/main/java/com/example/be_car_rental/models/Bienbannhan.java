package com.example.be_car_rental.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Entity
@Table(name = "bienbannhan")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Bienbannhan {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "idbienbannhan")
    private int idbienbannhan;

    @Column(name = "ngaynhan")
    private Timestamp ngaynhan;

    @Column(name = "sokmhientai")
    private int sokmhientai;

    @Column(name = "tinhtrangxe")
    private String tinhtrangxe;

    @Column(name = "idhopdong")
    private int idhopdong;

    @Column(name = "phantramxang")
    private int phantramxang;

    @Column(name = "xangtheolit")
    private int xangtheolit;

    @Column(name = "phukienkemtheo")
    private String phukienkemtheo;

    @Column(name = "nguoinhanxe")
    private String nguoinhanxe;

    @Column(name = "diadiemnhan")
    private String diadiemnhan;
}
