package com.example.be_car_rental.repositories;

import com.example.be_car_rental.models.Danhmuckiemtra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DanhmuckiemtraRepository extends JpaRepository<Danhmuckiemtra, Integer> {

    List<Danhmuckiemtra> findByIdbienbannhan(int idbienbannhan);
}
