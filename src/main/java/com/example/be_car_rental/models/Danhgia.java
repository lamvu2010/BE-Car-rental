package com.example.be_car_rental.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "danhgia")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Danhgia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "iddanhgia")
    private int iddanhgia;

    @Column(name = "noidungdanhgia")
    private String noidungdanhgia;

    @Column(name = "sao")
    private int sao;

    @Column(name = "idxe")
    private int idxe;

    @Column(name = "sodienthoai")
    private String sodienthoai;
}
