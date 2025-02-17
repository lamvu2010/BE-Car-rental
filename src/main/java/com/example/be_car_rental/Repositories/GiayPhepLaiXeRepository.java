package com.example.be_car_rental.Repositories;

import com.example.be_car_rental.Models.GiayPhepLaiXe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface GiayPhepLaiXeRepository extends JpaRepository<GiayPhepLaiXe, Integer> {

    @Modifying
    @Query("update GiayPhepLaiXe g set g.trangThai = false " +
            "where g.soDienThoai = :soDienThoai and g.idGiayPhepLaiXe != :idGiayPhepLaiXe ")
    void updateTrangThaiGPLX(@Param("soDienThoai") String soDienThoai, @Param("idGiayPhepLaiXe") int idGiayPhepLaiXe);

    boolean existsBySoDienThoai(String soDienThoai);

    GiayPhepLaiXe findBySoDienThoaiAndTrangThai(String soDienThoai, boolean trangThai);
}
