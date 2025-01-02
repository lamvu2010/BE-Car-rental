package com.example.be_car_rental.repositories;

import com.example.be_car_rental.models.Dieukhoan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface DieukhoanRepository extends JpaRepository<Dieukhoan, Integer> {
    @Query("select tieude from Dieukhoan d where d.iddieukhoan = :iddieukhoan")
    String getTermTitle(@Param("iddieukhoan") int iddieukhoan);

    @Query(value = "call ds_chitiet_phienban_dieukhoan_dangapdung()", nativeQuery = true)
    List<Map<String, String>> ds_noidung_dieukhoan();
}
