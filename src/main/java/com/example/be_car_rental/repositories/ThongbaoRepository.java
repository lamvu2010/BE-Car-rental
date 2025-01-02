package com.example.be_car_rental.repositories;

import com.example.be_car_rental.models.Thongbao;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Page;

@Repository
public interface ThongbaoRepository extends JpaRepository<Thongbao, Long> {
    Page<Thongbao> findBySodienthoai(String sodienthoai, Pageable pageable);

    @Modifying
    @Query(value = "update Thongbao t set t.trangthai = 'daxem' where t.idthongbao = :idthongbao ")
    void updateTrangthaiToDaxem(long idthongbao);
}