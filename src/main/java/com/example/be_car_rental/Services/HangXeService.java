package com.example.be_car_rental.Services;

import com.example.be_car_rental.DTO.ApiResponse;
import com.example.be_car_rental.DTO.HangXeDto;
import com.example.be_car_rental.Models.HangXe;
import com.example.be_car_rental.Repositories.DongXeRepository;
import com.example.be_car_rental.Repositories.HinhAnhRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.be_car_rental.Repositories.HangXeRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class HangXeService {
    @Autowired
    private HangXeRepository hangXeRepository;

    @Autowired
    private DongXeRepository dongXeRepository;

    @Autowired
    private HinhAnhRepository hinhAnhRepository;

    @Transactional
    public ApiResponse<HangXe> insert(HangXeDto hangxedto) {
        if (hangxedto.getTenHangXe() == null || hangxedto.getTenHangXe().isEmpty()) {
            return new ApiResponse<>(false, "Tên hãng xe không được để trống", null);
        }

        if (hangXeRepository.existsByTenHangXe(hangxedto.getTenHangXe())) {
            return new ApiResponse<>(false, "Tên hãng xe đã tồn tại", null);
        }

        HangXe hangxe = new HangXe();
        hangxe.setTenHangXe(hangxedto.getTenHangXe());
        hangxe = hangXeRepository.save(hangxe);
        return new ApiResponse<>(true, "Thêm thành công", hangxe);
    }

    @Transactional
    public ApiResponse<List<HangXe>> getAll() {
        List<HangXe> list = new ArrayList<>();
        list = hangXeRepository.findAll();
        if (list.isEmpty()) {
            return new ApiResponse<>(false, "Danh sách trống", list);
        }
        return new ApiResponse<>(true,"Lấy danh sách thành công", list);
    }

    @Transactional
    public ApiResponse delete(int id) {
        if (!hangXeRepository.findById(id).isPresent()) {
            return new ApiResponse<>(false, "Id hãng xe không tồn tại", null);
        }
        if(dongXeRepository.existsByIdHangXe(id)){
            return new ApiResponse<>(false, "Hãng xe đã có những dòng xe trong hệ thống!!", null);
        }
        try{
        hangXeRepository.deleteById(id);
        hinhAnhRepository.deleteAllByIdForeignAndLoai(id,"hangxe");
        return new ApiResponse<>(true, "Xóa thành công", null);}
        catch (Exception e){
            return new ApiResponse(false, "Lỗi khi xóa", null);
        }
    }

    @Transactional
    public ApiResponse<HangXe> update(HangXeDto hangxedto){
        if (hangxedto.getTenHangXe() == null || hangxedto.getTenHangXe().isEmpty()) {
            return new ApiResponse<>(false, "Tên hãng xe không được để trống", null);
        }
        if(hangXeRepository.findById(hangxedto.getIdHangXe()).isEmpty()){
            return new ApiResponse<>(false,"Id hãng xe không tồn tại", null);
        }
        HangXe hangxe = new HangXe();
        hangxe = hangXeRepository.findById(hangxedto.getIdHangXe()).get();
        if (hangXeRepository.existsByTenHangXeAndIdHangXeNot(hangxedto.getTenHangXe(), hangxedto.getIdHangXe())) {
            return new ApiResponse<>(false, "Tên hãng xe đã tồn tại", null);
        }
        hangxe.setTenHangXe(hangxedto.getTenHangXe());
        hangxe = hangXeRepository.save(hangxe);
        return new ApiResponse<>(true,"Sửa thành công", hangxe);
    }
}