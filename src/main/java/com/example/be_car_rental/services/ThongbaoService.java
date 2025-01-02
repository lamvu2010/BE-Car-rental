package com.example.be_car_rental.services;

import com.example.be_car_rental.DTO.ApiResponse;
import com.example.be_car_rental.models.Thongbao;
import com.example.be_car_rental.repositories.ThongbaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ThongbaoService {

    @Autowired
    private ThongbaoRepository thongbaoRepository;

    @Transactional
    public ApiResponse<List<Thongbao>> findAll(int page,int size, String sdt){
        PageRequest pageRequest = PageRequest.of(page,size, Sort.by(Sort.Direction.DESC,"idthongbao"));
        Page<Thongbao> listThongbao = thongbaoRepository.findBySodienthoai(sdt, pageRequest);
        return new ApiResponse<>(
                true, // Giả sử có trường `success` để chỉ trạng thái thành công
                "Lấy danh sách thông báo thành công",
                listThongbao.getContent() // Trả về danh sách nội dung của trang
        );
    }

    @Transactional
    public ApiResponse setThongBaoThanhDaDoc (long idthongbao){
        try{
            thongbaoRepository.updateTrangthaiToDaxem(idthongbao);
            return new ApiResponse<>(true,"Cập nhật thành công", null);
        }catch (Exception e){
            return new ApiResponse(false,"Cập nhật thông báo thất bại",null);
        }
    }
}