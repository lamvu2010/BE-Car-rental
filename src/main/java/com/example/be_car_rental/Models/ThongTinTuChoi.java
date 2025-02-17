package com.example.be_car_rental.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Entity
@Table(name = "thong_tin_tu_choi")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ThongTinTuChoi {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idthongtintuchoi")
    private int idThongTinTuChoi;

    @Column(name = "thoigiantuchoi")
    private Timestamp thoiGianTuChoi;

    @Column(name = "lidotuchoi")
    private String liDoTuChoi;

    @Column(name = "idhopdong")
    private int idHopDong;

}
