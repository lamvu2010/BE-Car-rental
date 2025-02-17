package com.example.be_car_rental.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "thong_bao_su_co")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ThongBaoSuCo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idthongbaosuco")
    private int idThongBaoSuCo;

    @Column(name = "vitri")
    private String viTri;

    @Column(name = "lienlac")
    private String lienLac;

    @Column(name = "mota")
    private String moTa;

    @Column(name = "idhopdong")
    private int idHopDong;

}