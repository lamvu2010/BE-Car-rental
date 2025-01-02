package com.example.be_car_rental.services;

import com.example.be_car_rental.DTO.ApiResponse;
import com.example.be_car_rental.DTO.Hangxedto;
import com.example.be_car_rental.DTO.Imagedto;
import com.example.be_car_rental.models.Hangxe;
import com.example.be_car_rental.repositories.DongxeRepository;
import com.example.be_car_rental.repositories.HinhanhRepository;
import org.hibernate.annotations.ManyToAny;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.be_car_rental.repositories.HangxeRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class HangxeService {
    @Autowired
    private HangxeRepository hangxeRepository;

    @Autowired
    private DongxeRepository dongxeRepository;

    @Autowired
    private HinhanhRepository hinhanhRepository;

    @Transactional
    public ApiResponse<Hangxe> insert(Hangxedto hangxedto) {
        if (hangxedto.getTenhangxe() == null || hangxedto.getTenhangxe().isEmpty()) {
            return new ApiResponse<>(false, "Tên hãng xe không được để trống", null);
        }

        if (hangxeRepository.existsBytenhangxe(hangxedto.getTenhangxe())) {
            return new ApiResponse<>(false, "Tên hãng xe đã tồn tại", null);
        }

        Hangxe hangxe = new Hangxe();
        hangxe.setTenhangxe(hangxedto.getTenhangxe());
        hangxe = hangxeRepository.save(hangxe);
        return new ApiResponse<>(true, "Thêm thành công", hangxe);
    }

    @Transactional
    public ApiResponse<List<Hangxe>> getAll() {
        List<Hangxe> list = new ArrayList<>();
        list = hangxeRepository.findAll();
        if (list.isEmpty()) {
            return new ApiResponse<>(false, "Danh sách trống", list);
        }
        return new ApiResponse<>(true,"Lấy danh sách thành công", list);
    }

    @Transactional
    public ApiResponse delete(int id) {
        if (!hangxeRepository.findById(id).isPresent()) {
            return new ApiResponse<>(false, "Id hãng xe không tồn tại", null);
        }
        if(dongxeRepository.existsByIdhangxe(id)){
            return new ApiResponse<>(false, "Hãng xe đã có những dòng xe trong hệ thống!!", null);
        }
        try{
        hangxeRepository.deleteById(id);
        hinhanhRepository.deleteAllByIdForeignAndLoai(id,"hangxe");
        return new ApiResponse<>(true, "Xóa thành công", null);}
        catch (Exception e){
            return new ApiResponse(false, "Lỗi khi xóa", null);
        }
    }

    @Transactional
    public ApiResponse<Hangxe> update(Hangxedto hangxedto){
        if (hangxedto.getTenhangxe() == null || hangxedto.getTenhangxe().isEmpty()) {
            return new ApiResponse<>(false, "Tên hãng xe không được để trống", null);
        }
        if(hangxeRepository.findById(hangxedto.getIdhangxe()).isEmpty()){
            return new ApiResponse<>(false,"Id hãng xe không tồn tại", null);
        }
        Hangxe hangxe = new Hangxe();
        hangxe = hangxeRepository.findById(hangxedto.getIdhangxe()).get();
        if (hangxeRepository.existsByTenhangxeAndIdhangxeNot(hangxedto.getTenhangxe(), hangxedto.getIdhangxe())) {
            return new ApiResponse<>(false, "Tên hãng xe đã tồn tại", null);
        }
        hangxe.setTenhangxe(hangxedto.getTenhangxe());
        hangxe = hangxeRepository.save(hangxe);
        return new ApiResponse<>(true,"Sửa thành công", hangxe);
    }
}