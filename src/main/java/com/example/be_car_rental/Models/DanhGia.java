package com.example.be_car_rental.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "danh_gia")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DanhGia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "iddanhgia")
    private int idDanhGia;

    @Column(name = "noidungdanhgia")
    private String noiDungDanhGia;

    @Column(name = "sao")
    private int sao;

    @Column(name = "idxe")
    private int idXe;

    @Column(name = "sodienthoai")
    private String soDienThoai;

}
