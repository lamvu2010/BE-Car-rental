package com.example.be_car_rental.DTO;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Bienbangiaodto {
    private Timestamp ngaygiao;
    private int sokmhientai;
    private String tinhtrangxe;
    private int idhopdong;
    private int phantramxang;
    private int xangtheolit;
    private String diadiemgiao;
    private String nguoigiaoxe;
    private String phukienkemtheo;
}
