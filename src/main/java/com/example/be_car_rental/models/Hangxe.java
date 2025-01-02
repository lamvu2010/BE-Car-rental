package com.example.be_car_rental.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "hangxe")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Hangxe {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "idhangxe")
    private int idhangxe;

    @Column(name = "tenhangxe")
    private String tenhangxe;
}
