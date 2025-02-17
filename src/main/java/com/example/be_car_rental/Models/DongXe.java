package com.example.be_car_rental.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "dong_xe")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DongXe {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "iddongxe")
    private int idDongXe;

    @Column(name = "tendongxe")
    private String tenDongXe;

    @Column(name = "idhangxe", nullable = true)
    private int idHangXe;


}
