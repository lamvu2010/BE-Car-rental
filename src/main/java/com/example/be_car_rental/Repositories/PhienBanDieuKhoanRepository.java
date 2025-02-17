package com.example.be_car_rental.Repositories;

import com.example.be_car_rental.Models.PhienBanDieuKhoan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface PhienBanDieuKhoanRepository extends JpaRepository<PhienBanDieuKhoan, Integer> {
    boolean existsByIdDieuKhoan(int id);

    @Modifying
    @Query("update PhienBanDieuKhoan e set e.trangThai = false " +
            "where e.idDieuKhoan = :iddieukhoan and e.idPhienBanDieuKhoan != :idphienbandieukhoan")
    void updateTrangThaiPhienBanDieuKhoan(@Param("iddieukhoan") int iddieukhoan, @Param("idphienbandieukhoan") int idphienbandieukhoan);

    boolean existsByIdDieuKhoanAndPhienBan(int iddieukhoan, String phienban);

    PhienBanDieuKhoan findByIdDieuKhoanAndTrangThai(int iddieukhoan, boolean trangthai);

    @Query(value = "select phienBan, ngayCapNhat, trangThai  " +
            "from PhienBanDieuKhoan p where p.idDieuKhoan = :iddieukhoan " +
            "order by p.ngayCapNhat desc", nativeQuery = true)
    List<Map<?,?>> listVersions(@Param("iddieukhoan") int iddieukhoan);

    @Query("select phienBan from PhienBanDieuKhoan p where p.idDieuKhoan = :iddieukhoan and p.trangThai = true")
    String getVersionActive(@Param("iddieukhoan") int iddieukhoan);

    @Query("select noiDung from PhienBanDieuKhoan p where p.idDieuKhoan = :iddieukhoan and p.phienBan = :phienban")
    String getDetail(@Param("iddieukhoan") int iddieukhoan, @Param("phienban") String phienban);
}