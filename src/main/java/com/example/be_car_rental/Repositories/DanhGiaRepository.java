package com.example.be_car_rental.Repositories;

import com.example.be_car_rental.Models.DanhGia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface DanhGiaRepository extends JpaRepository<DanhGia, Integer> {

    @Query(value = "call sp_danh_sach_danh_gia(:idxe, :page, :pageSize)", nativeQuery = true)
    List<Map<String, Object>> lay_ds_danhgia(@Param("idxe") int idxe,
                                             @Param("page") int page,
                                             @Param("pageSize") int pageSize);
}
