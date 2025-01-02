package com.example.be_car_rental.repositories;

import com.example.be_car_rental.models.Phuphiphatsinh;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhuphiphatsinhRepository extends JpaRepository<Phuphiphatsinh, Integer> {

    Phuphiphatsinh findByIdxe(int idxe);

    boolean existsByIdxe(int idxe);
}
