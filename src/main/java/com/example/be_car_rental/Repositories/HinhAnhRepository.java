package com.example.be_car_rental.Repositories;
import com.example.be_car_rental.Models.HinhAnh;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HinhAnhRepository extends JpaRepository<HinhAnh, Integer> {

    List<HinhAnh> findByIdForeignAndLoai(int id, String loai);

    void deleteAllByIdForeignAndLoai(int id, String loai);

    HinhAnh findFirstByIdForeignAndLoai (int id, String loai);
}