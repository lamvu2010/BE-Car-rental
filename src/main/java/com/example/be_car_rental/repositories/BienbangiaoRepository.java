package com.example.be_car_rental.repositories;

import com.example.be_car_rental.models.Bienbangiao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BienbangiaoRepository extends JpaRepository<Bienbangiao, Integer> {

    boolean existsByIdhopdong(int idhopdong);

    Bienbangiao findByIdhopdong(int idhopdong);
}
