package com.example.be_car_rental.repositories;
import com.example.be_car_rental.models.Hinhanh;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HinhanhRepository extends JpaRepository<Hinhanh, Integer> {

    List<Hinhanh> findByIdForeignAndLoai(int id, String loai);

    void deleteAllByIdForeignAndLoai(int id, String loai);

    Hinhanh findFirstByIdForeignAndLoai (int id, String loai);
}