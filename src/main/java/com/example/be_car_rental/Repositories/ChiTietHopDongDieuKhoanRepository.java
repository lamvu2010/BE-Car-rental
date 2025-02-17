package com.example.be_car_rental.Repositories;

import com.example.be_car_rental.Models.ChiTietHopDongDieuKhoan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChiTietHopDongDieuKhoanRepository extends JpaRepository<ChiTietHopDongDieuKhoan, Integer> {
}
