package com.example.be_car_rental.Repositories;

import com.example.be_car_rental.Models.BienBanGiao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BienBanGiaoRepository extends JpaRepository<BienBanGiao, Integer> {

    boolean existsByIdHopDong(int idHopDong);

    BienBanGiao findByIdHopDong(int idHopDong);
}
