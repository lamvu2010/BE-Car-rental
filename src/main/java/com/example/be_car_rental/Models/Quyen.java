package com.example.be_car_rental.Models;

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
    private int idQuyen;

    @Column(name = "tenquyen")
    private String tenQuyen;

}
