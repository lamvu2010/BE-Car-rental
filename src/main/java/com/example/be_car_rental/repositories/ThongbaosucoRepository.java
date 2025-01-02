package com.example.be_car_rental.repositories;

import com.example.be_car_rental.models.Thongbaosuco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ThongbaosucoRepository extends JpaRepository<Thongbaosuco, Integer> {
}
