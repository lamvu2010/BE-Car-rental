package com.example.be_car_rental.Repositories;

import com.example.be_car_rental.Models.ThongTinThanhToan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ThongTinThanhToanRepository extends JpaRepository<ThongTinThanhToan, Long> {

    List<ThongTinThanhToan> findByIdHopDong(int idhopdong);
}
