package com.example.be_car_rental.repositories;

import com.example.be_car_rental.models.Hoadon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HoadonRepository extends JpaRepository<Hoadon, Integer> {
}
