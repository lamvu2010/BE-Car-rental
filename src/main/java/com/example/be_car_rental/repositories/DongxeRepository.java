package com.example.be_car_rental.repositories;

import com.example.be_car_rental.DTO.Dongxedto;
import com.example.be_car_rental.models.Dongxe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public interface DongxeRepository extends JpaRepository<Dongxe,Integer> {

    boolean existsByIdhangxe(int idhangxe);

    boolean existsByTendongxe(String tendongxe);

    boolean existsByTendongxeAndIddongxeNot(String tendongxe, int iddongxe);

    boolean existsByTendongxeAndIdhangxe(String tendongxe, int idhangxe);

    Optional<Dongxe> findBytendongxe(String tendongxe);

    @Query(value = "call ds_dongxe();",nativeQuery = true)
    List<Map<?,?>> getAllDongXe();

    List<Dongxe> findByIdhangxe(int idhangxe);
}
