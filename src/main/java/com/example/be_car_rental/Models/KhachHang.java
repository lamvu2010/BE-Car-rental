package com.example.be_car_rental.Models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Entity
@Table(name = "khach_hang")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class KhachHang {
    @Id
    @Column(name = "sodienthoai")
    private String soDienThoai;

    @Column(name = "hovaten")
    private String hoVaTen;

    @Column(name = "email")
    private String email;

    @Column(name = "ngaysinh")
    private Date ngaySinh;

    @Column(name = "cccd")
    private String cccd;

}
