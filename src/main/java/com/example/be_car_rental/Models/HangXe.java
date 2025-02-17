package com.example.be_car_rental.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "hang_xe")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HangXe {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "idhangxe")
    private int idHangXe;

    @Column(name = "tenhangxe")
    private String tenHangXe;

}
