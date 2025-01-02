package com.example.be_car_rental.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Entity
@Table(name = "khachhang")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Khachhang {
    @Id
    @Column(name = "sodienthoai")
    private String sodienthoai;

    @Column(name = "hovaten")
    private String hovaten;

    @Column(name = "email")
    private String email;

    @Column(name = "ngaysinh")
    private Date ngaysinh;

    @Column(name = "cccd")
    private String cccd;
}
