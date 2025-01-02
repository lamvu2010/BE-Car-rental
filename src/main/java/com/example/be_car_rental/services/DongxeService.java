package com.example.be_car_rental.services;

import com.example.be_car_rental.DTO.ApiResponse;
import com.example.be_car_rental.DTO.Dongxedto;
import com.example.be_car_rental.models.Dongxe;
import com.example.be_car_rental.repositories.DongxeRepository;
import com.example.be_car_rental.repositories.HangxeRepository;
import com.example.be_car_rental.repositories.XeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class DongxeService {
    @Autowired
    private DongxeRepository dongxeRepository;

    @Autowired
    private HangxeRepository hangxeRepository;

    @Autowired
    private XeRepository xeRepository;

    @Transactional
    public ApiResponse<Dongxe> insert(Dongxedto dongxedto) {
        if (!hangxeRepository.existsById(dongxedto.getIdhangxe())) {
            return new ApiResponse<>(false, "Hãng xe không tồn tại!", null);
        }
        if (dongxeRepository.existsByTendongxeAndIdhangxe(dongxedto.getTendongxe(), dongxedto.getIdhangxe())) {
            return new ApiResponse<>(false, "Dòng xe đã tồn tại", null);
        }
        if (dongxedto.getTendongxe() == null || dongxedto.getTendongxe() == "") {
            return new ApiResponse<>(false, "Tên dòng xe không được để trống", null);
        }
        Dongxe dongxe = new Dongxe();
        dongxe.setTendongxe(dongxedto.getTendongxe());
        dongxe.setIdhangxe(dongxedto.getIdhangxe());
        dongxe = dongxeRepository.save(dongxe);
        return new ApiResponse<>(true, "Thêm thành công", dongxe);
    }

    @Transactional
    public ApiResponse<Dongxe> update(Dongxedto dongxedto) {
        if (!dongxeRepository.existsById(dongxedto.getIddongxe())) {
            return new ApiResponse<>(false, "Id dòng xe không tồn tại", null);
        }
        if (!hangxeRepository.existsById(dongxedto.getIdhangxe())) {
            return new ApiResponse<>(false, "Hãng xe không tồn tại!", null);
        }
        if (dongxedto.getTendongxe() == null || dongxedto.getTendongxe() == "") {
            return new ApiResponse<>(false, "Tên dòng xe không được để trống", null);
        }
        if (dongxeRepository.existsByTendongxeAndIddongxeNot(dongxedto.getTendongxe(), dongxedto.getIddongxe()) &&
                dongxeRepository.existsByTendongxeAndIdhangxe(dongxedto.getTendongxe(), dongxedto.getIdhangxe())) {
            return new ApiResponse<>(false, "Dòng xe đã tồn tại", null);
        }
        Dongxe dongxe = new Dongxe();
        dongxe.setIddongxe(dongxedto.getIddongxe());
        dongxe.setTendongxe(dongxedto.getTendongxe());
        dongxe.setIdhangxe(dongxedto.getIdhangxe());
        dongxe = dongxeRepository.save(dongxe);
        return new ApiResponse<>(true, "Sửa thành công", dongxe);
    }

    @Transactional
    public ApiResponse delete(int id) {
        if (!dongxeRepository.existsById(id)) {
            return new ApiResponse<>(false, "Id dòng xe không tồn tại", null);
        }
        if (xeRepository.existsByIddongxe(id)) {
            return new ApiResponse<>(false, "Dòng xe đã xuất hiện tại các xe trong hệ thống!!", null);
        }
        try {
            dongxeRepository.deleteById(id);
            return new ApiResponse<>(true, "Xóa thành công", null);
        } catch (Exception e) {
            return new ApiResponse<>(false, "Xóa thất bại" + e.getMessage(), null);
        }
    }

    @Transactional
    public ApiResponse<List<Map<?, ?>>> getAll() {
        List<Map<?, ?>> dongxedtoList = dongxeRepository.getAllDongXe();
        if (dongxedtoList.isEmpty()) {
            return new ApiResponse<>(false, "Danh sách rỗng", dongxedtoList);
        }
        return new ApiResponse<>(true, "Lấy thành công", dongxedtoList);
    }

    @Transactional
    public ApiResponse<List<Dongxe>> getDongXeByIdHangXe(int idhangxe){
        List<Dongxe> list = new ArrayList<>();
        list = dongxeRepository.findByIdhangxe(idhangxe);
        if(list.isEmpty()){
            return new ApiResponse<>(false, "Danh sách trống", list);
        }
        return new ApiResponse<>(true,"Lấy danh sách thành công", list);
    }
}
