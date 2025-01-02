package com.example.be_car_rental.repositories;

import com.example.be_car_rental.models.Hopdong;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Repository
public interface HopdongRepository extends JpaRepository<Hopdong, Integer> {

    @Query(value = "call ds_tatca_hopdong()",nativeQuery = true)
    List<Map<?,?>> dsTatCaHopDong();

    @Query(value = "call ds_hopdong_sodienthoai(:sdt)",nativeQuery = true)
    List<Map<?,?>> dsHopDongTheoSdt(@Param("sdt") String sdt);

    //Lấy chi tiết hợp đồng
    @Query(value = "call ct_hopdong(:id)", nativeQuery = true)
    Map<String,Object> ctHopDong(@Param("id") int id);

    // Áp dụng các phiên bản điều khoản đang áp dụng vào hợp đồng
    @Query(value = "call apply_dieukhoan_hopdong(:idhopdong)",nativeQuery = true)
    void applyDieuKhoanHopDong(@Param("idhopdong") int id);

    // Lấy danh sách hợp đồng dựa vào trạng thái
    @Query(value = "call ds_hopdong_trangthai(:in_trangthai)", nativeQuery = true)
    List<Map<String, Object>> ds_hopdong_trangthai(@Param("in_trangthai") String trangthai);

    // Lấy danh sách hợp đồng dựa vào số điện thoại và trạng thái
    @Query(value = "call ds_hopdong_sodienthoai_trangthai(:sdt,:in_trangthai)", nativeQuery = true)
    List<Map<String, Object>> ds_hopdong_sdt_trangthai(@Param("in_trangthai") String trangthai, @Param("sdt") String sdt);

    @Query(value = "call ds_hopdong_sodienthoai_daduyet(:sdt)", nativeQuery = true)
    List<Map<String,Object>> ds_hopdong_user_daduyet(@Param("sdt") String sdt);


    @Procedure(procedureName = "capnhat_trangthai_hopdong_xe")
    void capNhatHopDongHetHanCoc();

    @Query(value = "call ds_hopdong_hethan_datcoc()", nativeQuery = true)
    List<Map<String,Object>> ds_hopdong_hethan_datcoc();

    @Modifying
    @Query("delete from  Hopdong h where h.sodienthoai = :sodienthoai and h.trangthai = 'hethan' ")
    void xoaHopDongHetHan(@Param("sodienthoai") String sodienthoai);

    @Modifying
    @Query("delete from  Hopdong h where h.sodienthoai = :sodienthoai and h.trangthai = 'tuchoi' ")
    void xoaHopDongTuChoi(@Param("sodienthoai") String sodienthoai);

    Hopdong findByIdhopdongAndSodienthoai(int id, String sodienthoai);
}
