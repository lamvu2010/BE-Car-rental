package com.example.be_car_rental.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "hinhanh")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Hinhanh {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idhinhanh")
    private int idhinhanh;

    @Column(name = "tenhinhanh")
    private String tenhinhanh;

    @Column(name = "tenduynhat")
    private String tenduynhat;

    @Column(name = "path")
    private String path;

    @Column(name = "id")
    private int idForeign;

    @Column(name = "loai")
    private String loai;
}
