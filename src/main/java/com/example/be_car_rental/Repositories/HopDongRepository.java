package com.example.be_car_rental.Repositories;

import com.example.be_car_rental.Models.HopDong;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface HopDongRepository extends JpaRepository<HopDong, Integer> {

    @Query(value = "call sp_danh_sach_tat_ca_hop_dong()",nativeQuery = true)
    List<Map<?,?>> dsTatCaHopDong();

    @Query(value = "call sp_danh_sach_hop_dong_so_dien_thoai(:sdt)",nativeQuery = true)
    List<Map<?,?>> dsHopDongTheoSdt(@Param("sdt") String sdt);

    //Lấy chi tiết hợp đồng
    @Query(value = "call sp_chi_tiet_hop_dong(:id)", nativeQuery = true)
    Map<String,Object> ctHopDong(@Param("id") int id);

    // Áp dụng các phiên bản điều khoản đang áp dụng vào hợp đồng
    @Query(value = "call sp_apply_dieu_khoan_hop_dong(:idhopdong)",nativeQuery = true)
    void applyDieuKhoanHopDong(@Param("idhopdong") int id);

    // Lấy danh sách hợp đồng dựa vào trạng thái
    @Query(value = "call sp_danh_sach_hop_dong_trang_thai(:in_trangthai)", nativeQuery = true)
    List<Map<String, Object>> ds_hopdong_trangthai(@Param("in_trangthai") String trangthai);

    // Lấy danh sách hợp đồng dựa vào số điện thoại và trạng thái
    @Query(value = "call sp_danh_sach_hop_dong_so_dien_thoai_trang_thai(:sdt,:in_trangthai)", nativeQuery = true)
    List<Map<String, Object>> ds_hopdong_sdt_trangthai(@Param("in_trangthai") String trangthai, @Param("sdt") String sdt);

    @Query(value = "call sp_danh_sach_hop_dong_so_dien_thoai_da_duyet(:sdt)", nativeQuery = true)
    List<Map<String,Object>> ds_hopdong_user_daduyet(@Param("sdt") String sdt);


    @Procedure(procedureName = "sp_cap_nhat_trang_thai_hop_dong_xe")
    void capNhatHopDongHetHanCoc();

    @Query(value = "call sp_danh_sach_hop_dong_het_han_dat_coc()", nativeQuery = true)
    List<Map<String,Object>> ds_hopdong_hethan_datcoc();

    @Modifying
    @Query("delete from  HopDong h where h.soDienThoai = :sodienthoai and h.trangThai = 'hethan' ")
    void xoaHopDongHetHan(@Param("sodienthoai") String sodienthoai);

    @Modifying
    @Query("delete from  HopDong h where h.soDienThoai = :sodienthoai and h.trangThai = 'tuchoi' ")
    void xoaHopDongTuChoi(@Param("sodienthoai") String sodienthoai);

    HopDong findByIdHopDongAndSoDienThoai(int id, String sodienthoai);
}
