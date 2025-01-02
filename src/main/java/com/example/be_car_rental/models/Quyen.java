package com.example.be_car_rental.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "quyen")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Quyen {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "idquyen")
    private int idquyen;

    @Column(name = "tenquyen")
    private String tenquyen;

}
