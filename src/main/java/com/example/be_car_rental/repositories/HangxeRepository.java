package com.example.be_car_rental.repositories;

import com.example.be_car_rental.models.Hangxe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HangxeRepository extends JpaRepository<Hangxe,Integer> {
    boolean existsByTenhangxeAndIdhangxeNot(String tenHangXe, int idhangxe);

    boolean existsBytenhangxe(String tenhangxe);

}
