package com.example.be_car_rental.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "hinh_anh")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HinhAnh {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idhinhanh")
    private int idHinhAnh;

    @Column(name = "tenhinhanh")
    private String tenHinhAnh;

    @Column(name = "tenduynhat")
    private String tenDuyNhat;

    @Column(name = "path")
    private String path;

    @Column(name = "id")
    private int idForeign;

    @Column(name = "loai")
    private String loai;

}
