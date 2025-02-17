package com.example.be_car_rental.Repositories;

import com.example.be_car_rental.Models.DieuKhoan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface DieuKhoanRepository extends JpaRepository<DieuKhoan, Integer> {
    @Query("select tieuDe from DieuKhoan d where d.idDieuKhoan = :idDieuKhoan")
    String getTermTitle(@Param("idDieuKhoan") int idDieuKhoan);

    @Query(value = "call sp_danh_sach_chi_tiet_phien_ban_dieu_khoan_dang_ap_dung()", nativeQuery = true)
    List<Map<String, String>> danhSachNoiDungDieuKhoan();
}
