package com.example.be_car_rental.repositories;

import com.example.be_car_rental.models.Bienbannhan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BienbannhanRepository extends JpaRepository<Bienbannhan, Integer> {

    boolean existsByIdhopdong(int idhopdong);
}
