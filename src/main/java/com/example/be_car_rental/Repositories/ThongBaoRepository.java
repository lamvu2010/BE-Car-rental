package com.example.be_car_rental.Repositories;

import com.example.be_car_rental.Models.ThongBao;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Page;

@Repository
public interface ThongBaoRepository extends JpaRepository<ThongBao, Long> {
    Page<ThongBao> findBySoDienThoai(String sodienthoai, Pageable pageable);

    @Modifying
    @Query(value = "update ThongBao t set t.trangThai = 'daxem' where t.idThongBao = :idThongBao ")
    void updateTrangthaiToDaxem(long idThongBao);
}