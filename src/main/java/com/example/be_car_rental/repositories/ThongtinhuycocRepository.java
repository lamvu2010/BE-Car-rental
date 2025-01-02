package com.example.be_car_rental.repositories;

import com.example.be_car_rental.models.Thongtinhuycoc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ThongtinhuycocRepository extends JpaRepository<Thongtinhuycoc, Integer> {

    Thongtinhuycoc findByIdhopdong(int idhopdong);
}
