package com.example.be_car_rental.Repositories;

import com.example.be_car_rental.Models.PhuPhiPhatSinh;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhuPhiPhatSinhRepository extends JpaRepository<PhuPhiPhatSinh, Integer> {

    PhuPhiPhatSinh findByIdXe(int idxe);

    boolean existsByIdXe(int idxe);
}
