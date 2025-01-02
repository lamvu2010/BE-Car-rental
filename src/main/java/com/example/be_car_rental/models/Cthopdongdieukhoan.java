package com.example.be_car_rental.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "cthopdongdieukhoan")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cthopdongdieukhoan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idcthopdongdieukhoan")
    private int idcthopdongdieukhoan;

    @Column(name = "idhopdong")
    private int idhopdong;

    @Column(name = "idphienbandieukhoan")
    private int idphienbandieukhoan;
}
