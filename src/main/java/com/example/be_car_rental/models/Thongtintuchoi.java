package com.example.be_car_rental.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Entity
@Table(name = "thongtintuchoi")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Thongtintuchoi {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idthongtintuchoi")
    private int idthongtintuchoi;

    @Column(name = "thoigiantuchoi")
    private Timestamp thoigiantuchoi;

    @Column(name = "lidotuchoi")
    private String lidotuchoi;

    @Column(name = "idhopdong")
    private int idhopdong;
}
