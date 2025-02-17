package com.example.be_car_rental.Repositories;

import com.example.be_car_rental.Models.ThongTinHuyCoc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ThongTinHuyCocRepository extends JpaRepository<ThongTinHuyCoc, Integer> {

    ThongTinHuyCoc findByIdHopDong(int idhopdong);
}
