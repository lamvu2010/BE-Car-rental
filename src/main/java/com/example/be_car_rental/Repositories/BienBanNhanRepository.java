package com.example.be_car_rental.Repositories;

import com.example.be_car_rental.Models.BienBanNhan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BienBanNhanRepository extends JpaRepository<BienBanNhan, Integer> {

    boolean existsByIdHopDong(int idHopDong);
}
