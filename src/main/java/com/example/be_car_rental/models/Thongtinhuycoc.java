package com.example.be_car_rental.models;

import jakarta.persistence.*;
import jdk.jfr.Enabled;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Entity
@Table(name = "thongtinhuycoc")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Thongtinhuycoc {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idthongtinhuycoc")
    private int idthongtinhuycoc;

    @Column(name = "thoigianhuycoc")
    private Timestamp thoigianhuycoc;

    @Column(name = "sotienhoanlai")
    private BigDecimal sotienhoanlai;

    @Column(name = "lidohuycoc")
    private String lidohuycoc;

    @Column(name = "trangthaihoantien")
    private String trangthaihoantien;

    @Column(name = "idhopdong")
    private int idhopdong;

    @Column(name = "phuongthuc")
    private String phuongthuc;

    @Column(name = "nganhang")
    private String nganhang;

    @Column(name = "sotaikhoan")
    private String sotaikhoan;
}
