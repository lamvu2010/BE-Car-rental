package com.example.be_car_rental.Repositories;

import com.example.be_car_rental.Models.DanhMucKiemTra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DanhMucKiemTraRepository extends JpaRepository<DanhMucKiemTra, Integer> {

    List<DanhMucKiemTra> findByIdBienBanNhan(int idBienBanNhan);
}
