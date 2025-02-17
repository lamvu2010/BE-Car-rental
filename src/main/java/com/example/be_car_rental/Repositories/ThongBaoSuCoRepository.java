package com.example.be_car_rental.Repositories;

import com.example.be_car_rental.Models.ThongBaoSuCo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ThongBaoSuCoRepository extends JpaRepository<ThongBaoSuCo, Integer> {
}
