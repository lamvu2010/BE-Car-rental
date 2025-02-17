package com.example.be_car_rental.Repositories;

import com.example.be_car_rental.Models.DongXe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public interface DongXeRepository extends JpaRepository<DongXe,Integer> {

    boolean existsByIdHangXe(int idHangXe);

    boolean existsByTenDongXe(String tenDongXe);

    boolean existsByTenDongXeAndIdDongXeNot(String tenDongXe, int idDongXe);

    boolean existsByTenDongXeAndIdHangXe(String tenDongXe, int idHangXe);

    Optional<DongXe> findByTenDongXe(String tenDongXe);

    @Query(value = "call sp_danh_sach_dong_xe();",nativeQuery = true)
    List<Map<?,?>> getAllDongXe();

    List<DongXe> findByIdHangXe(int idHangXe);
}
