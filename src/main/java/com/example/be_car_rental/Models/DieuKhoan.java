package com.example.be_car_rental.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Entity
@Table(name = "dieu_khoan")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DieuKhoan {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "iddieukhoan")
    private int idDieuKhoan;

    @Column(name = "ngaytao")
    private Date ngayTao;

    @Column(name = "tieude")
    private String tieuDe;

}
