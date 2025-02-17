package com.example.be_car_rental.Repositories;

import com.example.be_car_rental.Models.HangXe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HangXeRepository extends JpaRepository<HangXe,Integer> {
    boolean existsByTenHangXeAndIdHangXeNot(String tenHangXe, int idhangxe);

    boolean existsByTenHangXe(String tenHangXe);

}
