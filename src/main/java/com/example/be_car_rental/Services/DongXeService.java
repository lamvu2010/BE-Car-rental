package com.example.be_car_rental.Services;

import com.example.be_car_rental.DTO.ApiResponse;
import com.example.be_car_rental.DTO.DongXeDto;
import com.example.be_car_rental.Models.DongXe;
import com.example.be_car_rental.Repositories.DongXeRepository;
import com.example.be_car_rental.Repositories.HangXeRepository;
import com.example.be_car_rental.Repositories.XeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class DongXeService {
    @Autowired
    private DongXeRepository dongXeRepository;

    @Autowired
    private HangXeRepository hangXeRepository;

    @Autowired
    private XeRepository xeRepository;

    @Transactional
    public ApiResponse<DongXe> insert(DongXeDto dongxedto) {
        if (!hangXeRepository.existsById(dongxedto.getIdDongXe())) {
            return new ApiResponse<>(false, "Hãng xe không tồn tại!", null);
        }
        if (dongXeRepository.existsByTenDongXeAndIdHangXe(dongxedto.getTenDongXe(), dongxedto.getIdHangXe())) {
            return new ApiResponse<>(false, "Dòng xe đã tồn tại", null);
        }
        if (dongxedto.getTenDongXe() == null || dongxedto.getTenDongXe() == "") {
            return new ApiResponse<>(false, "Tên dòng xe không được để trống", null);
        }
        DongXe dongxe = new DongXe();
        dongxe.setTenDongXe(dongxedto.getTenDongXe());
        dongxe.setIdHangXe(dongxedto.getIdHangXe());
        dongxe = dongXeRepository.save(dongxe);
        return new ApiResponse<>(true, "Thêm thành công", dongxe);
    }

    @Transactional
    public ApiResponse<DongXe> update(DongXeDto dongxedto) {
        if (!dongXeRepository.existsById(dongxedto.getIdDongXe())) {
            return new ApiResponse<>(false, "Id dòng xe không tồn tại", null);
        }
        if (!hangXeRepository.existsById(dongxedto.getIdHangXe())) {
            return new ApiResponse<>(false, "Hãng xe không tồn tại!", null);
        }
        if (dongxedto.getTenDongXe() == null || dongxedto.getTenDongXe() == "") {
            return new ApiResponse<>(false, "Tên dòng xe không được để trống", null);
        }
        if (dongXeRepository.existsByTenDongXeAndIdDongXeNot(dongxedto.getTenDongXe(), dongxedto.getIdDongXe()) &&
                dongXeRepository.existsByTenDongXeAndIdHangXe(dongxedto.getTenDongXe(), dongxedto.getIdHangXe())) {
            return new ApiResponse<>(false, "Dòng xe đã tồn tại", null);
        }
        DongXe dongxe = new DongXe();
        dongxe.setIdDongXe(dongxedto.getIdDongXe());
        dongxe.setTenDongXe(dongxedto.getTenDongXe());
        dongxe.setIdHangXe(dongxedto.getIdHangXe());
        dongxe = dongXeRepository.save(dongxe);
        return new ApiResponse<>(true, "Sửa thành công", dongxe);
    }

    @Transactional
    public ApiResponse delete(int id) {
        if (!dongXeRepository.existsById(id)) {
            return new ApiResponse<>(false, "Id dòng xe không tồn tại", null);
        }
        if (xeRepository.existsByIdDongXe(id)) {
            return new ApiResponse<>(false, "Dòng xe đã xuất hiện tại các xe trong hệ thống!!", null);
        }
        try {
            dongXeRepository.deleteById(id);
            return new ApiResponse<>(true, "Xóa thành công", null);
        } catch (Exception e) {
            return new ApiResponse<>(false, "Xóa thất bại" + e.getMessage(), null);
        }
    }

    @Transactional
    public ApiResponse<List<Map<?, ?>>> getAll() {
        List<Map<?, ?>> dongxedtoList = dongXeRepository.getAllDongXe();
        if (dongxedtoList.isEmpty()) {
            return new ApiResponse<>(false, "Danh sách rỗng", dongxedtoList);
        }
        return new ApiResponse<>(true, "Lấy thành công", dongxedtoList);
    }

    @Transactional
    public ApiResponse<List<DongXe>> getDongXeByIdHangXe(int idhangxe){
        List<DongXe> list = new ArrayList<>();
        list = dongXeRepository.findByIdHangXe(idhangxe);
        if(list.isEmpty()){
            return new ApiResponse<>(false, "Danh sách trống", list);
        }
        return new ApiResponse<>(true,"Lấy danh sách thành công", list);
    }
}
