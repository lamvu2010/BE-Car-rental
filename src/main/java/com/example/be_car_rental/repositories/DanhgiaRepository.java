package com.example.be_car_rental.repositories;

import com.example.be_car_rental.models.Danhgia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface DanhgiaRepository extends JpaRepository<Danhgia, Integer> {

    @Query(value = "call lay_ds_danhgia(:idxe, :page, :pageSize)", nativeQuery = true)
    List<Map<String, Object>> lay_ds_danhgia(@Param("idxe") int idxe,
                                             @Param("page") int page,
                                             @Param("pageSize") int pageSize);
}
