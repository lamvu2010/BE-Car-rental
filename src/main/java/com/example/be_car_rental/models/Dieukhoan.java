package com.example.be_car_rental.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Entity
@Table(name = "dieukhoan")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Dieukhoan {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "iddieukhoan")
    private int iddieukhoan;

    @Column(name = "ngaytao")
    private Date ngaytao;

    @Column(name = "tieude")
    private String tieude;
}
