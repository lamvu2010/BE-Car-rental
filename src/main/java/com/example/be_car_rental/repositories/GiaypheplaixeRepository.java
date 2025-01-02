package com.example.be_car_rental.repositories;

import com.example.be_car_rental.models.Giaypheplaixe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface GiaypheplaixeRepository extends JpaRepository<Giaypheplaixe, Integer> {

    @Modifying
    @Query("update Giaypheplaixe g set g.trangthai = false " +
            "where g.sodienthoai = :sodienthoai and g.idgiaypheplaixe != :idgiaypheplaixe ")
    void updateTrangThaiGPLX(@Param("sodienthoai") String sodienthoai, @Param("idgiaypheplaixe") int idgiaypheplaixe);

    boolean existsBySodienthoai(String sodienthoai);

    Giaypheplaixe findBySodienthoaiAndTrangthai(String sodienthoai, boolean trangthai);
}
