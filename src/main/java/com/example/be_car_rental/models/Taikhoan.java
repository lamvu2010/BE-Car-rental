package com.example.be_car_rental.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Entity
@Table(name = "taikhoan")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Taikhoan {
    @Id
    @Column(name = "sodienthoai")
    private String sodienthoai;

    @Column(name = "matkhau")
    private String matkhau;

    @Column(name = "otp")
    private String otp;

    @Column(name = "thoigiantaoma")
    private Timestamp thoigiantaoma;

    @Column(name = "thoigianhethan")
    private Timestamp thoigianhethan;

    @Column(name = "trangthai")
    private Boolean trangthai;

    @Column(name = "idquyen")
    private int idquyen;

}
