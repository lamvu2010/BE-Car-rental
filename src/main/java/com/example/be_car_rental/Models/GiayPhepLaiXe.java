package com.example.be_car_rental.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Entity
@Table(name = "giay_phep_lai_xe")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GiayPhepLaiXe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idgiaypheplaixe")
    private int idGiayPhepLaiXe;

    @Column(name = "sogplx")
    private String soGiayPhepLaiXe;

    @Column(name = "hovaten")
    private String hoVaTen;

    @Column(name = "sodienthoai")
    private String soDienThoai;

    @Column(name = "trangthai")
    private boolean trangThai;

    @Column(name = "ngaysinh")
    private Date ngaySinh;

}
