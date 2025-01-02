package com.example.be_car_rental.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "dongxe")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Dongxe {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "iddongxe")
    private int iddongxe;

    @Column(name = "tendongxe")
    private String tendongxe;

    @Column(name = "idhangxe", nullable = true)
    private int idhangxe;

}
