package com.example.be_car_rental.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "danhmuckiemtra")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Danhmuckiemtra {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "iddanhmuckiemtra")
    private int iddanhmuckiemtra;

    @Column(name = "muckiemtra")
    private String muckiemtra;

    @Column(name = "trangthai")
    private String trangthai;

    @Column(name = "ghichu")
    private String ghichu;

    @Column(name = "chiphiphatsinh")
    private BigDecimal chiphiphatsinh;

    @Column(name = "idbienbannhan")
    private int idbienbannhan;
}