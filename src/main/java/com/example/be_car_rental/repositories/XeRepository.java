package com.example.be_car_rental.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.example.be_car_rental.models.Xe;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public interface XeRepository extends JpaRepository<Xe, Integer> {

    long count();

    boolean existsByIddongxe(int iddongxe);
    Optional<Xe> findByIddongxeAndBiensoxe(int iddongxe, String biensoxe);

    @Query(value = "call ds_xe()", nativeQuery = true)
    List<Map<?,?>> getAll();

    @Query(value = "call getXeById(:idxe)",nativeQuery = true)
    Map<?,?> getXeById(@Param("idxe") int id);

    @Query(value = "call get_ds_xe_thue()",nativeQuery = true)
    List<Map<?,?>> getDsXeChoThue();

    @Query(value = "call ds_biensoxe_iddongxe(:id)",nativeQuery = true)
    List<Map<?,?>> dsBienSoTheoIddongxe(@Param("id") int id);

    @Query(value = "call getPage(:currentPage, :itemsPerPage)", nativeQuery = true)
    List<Map<?,?>> getPage(@Param("currentPage") int currentPage, @Param("itemsPerPage") int itemsPerPage);

    @Query(value = "call ds_xe_de_xuat()", nativeQuery = true)
    List<Map<String,Object>> dsXeDeXuatTrangHome();
}