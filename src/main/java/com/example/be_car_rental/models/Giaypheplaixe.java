package com.example.be_car_rental.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Entity
@Table(name = "giaypheplaixe")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Giaypheplaixe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idgiaypheplaixe")
    private int idgiaypheplaixe;

    @Column(name = "sogplx")
    private String sogplx;

    @Column(name = "hovaten")
    private String hovaten;

    @Column(name = "sodienthoai")
    private String sodienthoai;

    @Column(name = "trangthai")
    private boolean trangthai;

    @Column(name = "ngaysinh")
    private Date ngaysinh;
}
