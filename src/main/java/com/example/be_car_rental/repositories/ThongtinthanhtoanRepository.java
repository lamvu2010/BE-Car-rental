package com.example.be_car_rental.repositories;

import com.example.be_car_rental.models.Thongtinthanhtoan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ThongtinthanhtoanRepository extends JpaRepository<Thongtinthanhtoan, Long> {

    List<Thongtinthanhtoan> findByIdhopdong(int idhopdong);
}
