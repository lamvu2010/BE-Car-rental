package com.example.be_car_rental.repositories;

import com.example.be_car_rental.models.Phienbandieukhoan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface PhienbandieukhoanRepository extends JpaRepository<Phienbandieukhoan, Integer> {
    boolean existsByIddieukhoan(int id);

    @Modifying
    @Query("update Phienbandieukhoan e set e.trangthai = false " +
            "where e.iddieukhoan = :iddieukhoan and e.idphienbandieukhoan != :idphienbandieukhoan")
    void updateTrangThaiPhienBanDieuKhoan(@Param("iddieukhoan") int iddieukhoan, @Param("idphienbandieukhoan") int idphienbandieukhoan);

    boolean existsByIddieukhoanAndPhienban(int iddieukhoan, String phienban);

    Phienbandieukhoan findByIddieukhoanAndTrangthai(int iddieukhoan, boolean trangthai);

    @Query(value = "select phienban, ngaycapnhat, trangthai  " +
            "from Phienbandieukhoan p where p.iddieukhoan = :iddieukhoan " +
            "order by p.ngaycapnhat desc", nativeQuery = true)
    List<Map<?,?>> listVersions(@Param("iddieukhoan") int iddieukhoan);

    @Query("select phienban from Phienbandieukhoan p where p.iddieukhoan = :iddieukhoan and p.trangthai = true")
    String getVersionActive(@Param("iddieukhoan") int iddieukhoan);

    @Query("select noidung from Phienbandieukhoan p where p.iddieukhoan = :iddieukhoan and p.phienban = :phienban")
    String getDetail(@Param("iddieukhoan") int iddieukhoan, @Param("phienban") String phienban);
}