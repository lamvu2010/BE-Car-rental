package com.example.be_car_rental.Services;

import com.example.be_car_rental.DTO.ApiResponse;
import com.example.be_car_rental.Models.ThongBao;
import com.example.be_car_rental.Repositories.ThongBaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ThongBaoService {

    @Autowired
    private ThongBaoRepository thongBaoRepository;

    @Transactional
    public ApiResponse<List<ThongBao>> findAll(int page, int size, String sdt){
        PageRequest pageRequest = PageRequest.of(page,size, Sort.by(Sort.Direction.DESC,"idThongBao"));
        Page<ThongBao> listThongbao = thongBaoRepository.findBySoDienThoai(sdt, pageRequest);
        return new ApiResponse<>(
                true, // Giả sử có trường `success` để chỉ trạng thái thành công
                "Lấy danh sách thông báo thành công",
                listThongbao.getContent() // Trả về danh sách nội dung của trang
        );
    }

    @Transactional
    public ApiResponse setThongBaoThanhDaDoc (long idthongbao){
        try{
            thongBaoRepository.updateTrangthaiToDaxem(idthongbao);
            return new ApiResponse<>(true,"Cập nhật thành công", null);
        }catch (Exception e){
            return new ApiResponse(false,"Cập nhật thông báo thất bại",null);
        }
    }
}